package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.restclient.IClient;

public class Job extends KubernetesResource {

	public static final String JOB_TEMPLATE_CONTAINERS = "spec.template.spec.containers";

	private static final String IMAGE = "image";

	public Job(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
		super(node, client, overrideProperties);
	}

	public Collection<String> getImages() {
		ModelNode node = get(JOB_TEMPLATE_CONTAINERS);
		if (node.getType() != ModelType.LIST) {
			return new ArrayList<String>();
		}
		Collection<String> list = new ArrayList<String>();
		for (ModelNode entry : node.asList()) {
			list.add(entry.get(IMAGE).asString());
		}
		return list;
	}

	public List<ModelNode> getContainers() {
		ModelNode node = get(JOB_TEMPLATE_CONTAINERS);
		if (node.getType() != ModelType.LIST) {
			return new ArrayList<ModelNode>();
		}
		return node.asList();
	}

}
