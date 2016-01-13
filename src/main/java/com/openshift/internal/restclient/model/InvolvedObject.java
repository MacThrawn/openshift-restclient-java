package com.openshift.internal.restclient.model;

import org.jboss.dmr.ModelNode;

import com.openshift.restclient.model.IInvolvedObject;

public class InvolvedObject implements IInvolvedObject {

	private final ModelNode modelNode;

	public InvolvedObject(ModelNode modelNode) {
		this.modelNode = modelNode;
	}

	@Override
	public String getName() {
		return modelNode.get("name").asString();
	}

	@Override
	public String getNamespace() {
		return modelNode.get("namespace").asString();
	}

	@Override
	public String getResourceVersion() {
		return modelNode.get("resourceVersion").asString();
	}

	@Override
	public String getKind() {
		return modelNode.get("kind").asString();
	}

}
