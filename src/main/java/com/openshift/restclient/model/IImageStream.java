/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient.model;

import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.restclient.images.DockerImageURI;

/**
 * @author Jeff Cantrill
 */
public interface IImageStream extends IResource {

	/**
	 * Retrieve the docker image URI for which this image repository
	 * is responsible
	 *
	 * @return
	 */
	DockerImageURI getDockerImageRepository();

	void setDockerImageRepository(DockerImageURI imageUri);

	/**
	 * Retrieves all repository tags from the ImageStream.
	 *
	 * @return
	 */
	List<IRepositoryImageStreamTag> getRepositoryImageStreamTags();

	public abstract void setTagReference(IImageStreamTagReference reference);
}
