/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.restclient.IClient;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IImageStream;
import com.openshift.restclient.model.IImageStreamTagReference;
import com.openshift.restclient.model.IObjectReference;
import com.openshift.restclient.model.IRepositoryImageStreamTag;

/**
 * @author Jeff Cantrill
 */
public class ImageStream extends KubernetesResource implements IImageStream {

	private static final String IMAGESTREAM_DOCKER_IMAGE_REPO = "spec.dockerImageRepository";
	private static final String IMAGESTREAM_TAGS = "spec.tags";
	private static final String IMAGESTREAM_REPO_TAGS = "status.tags";

	public ImageStream() {
		this(new ModelNode(), null, null);
	}

	public ImageStream(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
	}

	@Override
	public void setDockerImageRepository(DockerImageURI uri) {
		set(IMAGESTREAM_DOCKER_IMAGE_REPO, uri.getAbsoluteUri());
	}

	@Override
	public DockerImageURI getDockerImageRepository() {
		return new DockerImageURI(asString(IMAGESTREAM_DOCKER_IMAGE_REPO));
	}

	public List<IImageStreamTagReference> getTagReferences() {
		List<IImageStreamTagReference> tags = new ArrayList<>();
		ModelNode modelNode = get(IMAGESTREAM_TAGS);
		if (modelNode.getType() == ModelType.LIST) {
			List<ModelNode> list = modelNode.asList();
			for (ModelNode node : list) {
				final String name = node.get("name").asString();
				ModelNode fromNode = node.get("from");
				final ObjectReference or = new ObjectReference(
					fromNode.get("kind").asString(),
					fromNode.get("name").asString(),
					fromNode.get("namespace").asString());
				tags.add(new IImageStreamTagReference() {

					@Override
					public String getName() {
						return name;
					}

					@Override
					public IObjectReference getFrom() {
						return or;
					}
				});
			}
		}
		return tags;
	}

	@Override
	public void setTagReference(IImageStreamTagReference reference) {
		boolean replaced = false;
		ModelNode modelNode = get(IMAGESTREAM_TAGS);
		if (modelNode.getType() == ModelType.LIST) {
			List<ModelNode> list = modelNode.asList();
			for (ModelNode node : list) {
				if (reference.getName().equals(node.get("name").asString())) {
					createObjectReferenceNode(node.get("from"), reference);
					replaced = true;
					break;
				}
			}
		}
		if (!replaced) {
			if (modelNode.getType() != ModelType.LIST) {
				//modelNode.set(ModelType.LIST);
				modelNode.setEmptyList();
			}
			ModelNode tag = new ModelNode();

			tag.get("name").set(reference.getName());
			ModelNode from = tag.get("from");
			createObjectReferenceNode(from, reference);
			modelNode.add(tag);
		}
	}

	private void createObjectReferenceNode(ModelNode node, IImageStreamTagReference reference) {
		node.get("kind").set(reference.getFrom().getKind());
		node.get("name").set(reference.getFrom().getName());
		if (reference.getFrom().getNamespace() != null) {
			node.get("namespace").set(reference.getFrom().getNamespace());
		} else {
			node.remove("namespace");
		}
	}

	@Override
	public List<IRepositoryImageStreamTag> getRepositoryImageStreamTags() {
		List<IRepositoryImageStreamTag> tags = new ArrayList<>();
		ModelNode modelNode = get(IMAGESTREAM_REPO_TAGS);
		if (modelNode.getType() == ModelType.LIST) {
			List<ModelNode> list = modelNode.asList();
			for (ModelNode node : list) {
				RepositoryImageStreamTag tag = new RepositoryImageStreamTag(node.get("tag").asString());
				List<ModelNode> itemList = node.get("items").asList();
				for (ModelNode item : itemList) {
					tag.addImage(new RepositoryImageStreamImage(item));
				}

				tags.add(tag);
			}
		}
		return tags;
	}

}
