package com.openshift.internal.restclient.model;

import org.jboss.dmr.ModelNode;

import com.openshift.restclient.model.IResourceQuotaItem;
import com.openshift.restclient.utils.UnitConverter;

public class ResourceQuotaItem implements IResourceQuotaItem {

	private final ModelNode modelNode;

	public ResourceQuotaItem(ModelNode modelNode) {
		this.modelNode = modelNode;
	}

	@Override
	public long getCpu() {
		return UnitConverter.convert(modelNode.get("cpu").asString());
	}

	@Override
	public long getMemory() {
		return UnitConverter.convert(modelNode.get("memory").asString());
	}

	@Override
	public int getPersistentVolumeClaims() {
		return modelNode.get("persistentvolumeclaims").asInt();
	}

	@Override
	public int getPods() {
		return modelNode.get("pods").asInt();
	}

	@Override
	public int getReplicationControllers() {
		return modelNode.get("replicationcontrollers").asInt();
	}

	@Override
	public int getResourceQuotas() {
		return modelNode.get("resourcequotas").asInt();
	}

	@Override
	public int getSecrets() {
		return modelNode.get("secrets").asInt();
	}

	@Override
	public int getServices() {
		return modelNode.get("services").asInt();
	}

}
