package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.internal.restclient.model.deploy.DeploymentTrigger;
import com.openshift.internal.restclient.model.deploy.ImageChangeTrigger;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IDeploymentConfig;
import com.openshift.restclient.model.deploy.DeploymentTriggerType;
import com.openshift.restclient.model.deploy.IDeploymentTrigger;

/**
 * @author Jeff Cantrill
 */
public class DeploymentConfig extends PodTemplate implements IDeploymentConfig {

	private static final String DEPLOYMENTCONFIG_REPLICAS = "spec.replicas";
	private static final String DEPLOYMENTCONFIG_REPLICA_SELECTOR = "spec.selector";
	private static final String DEPLOYMENTCONFIG_TRIGGERS = "spec.triggers";
	private static final String DEPLOYMENTCONFIG_STRATEGY = "spec.strategy.type";
	private static final String DEPLOYMENTCONFIG_STATUS_LATEST_VERSION = "status.latestVersion";

	private static final String TYPE = "type";

	private final Map<String, String[]> propertyKeys;

	public DeploymentConfig(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
		this.propertyKeys = propertyKeys;
	}

	@Override
	public Map<String, String> getReplicaSelector() {
		return asMap(DEPLOYMENTCONFIG_REPLICA_SELECTOR);
	}

	@Override
	public void setReplicaSelector(Map<String, String> selector) {
		get(DEPLOYMENTCONFIG_REPLICA_SELECTOR).clear();
		set(DEPLOYMENTCONFIG_REPLICA_SELECTOR, selector);
	}

	@Override
	public void setReplicaSelector(String key, String value) {
		Map<String, String> selector = new HashMap<>();
		selector.put(key, value);
		setReplicaSelector(selector);
	}

	@Override
	public List<String> getTriggerTypes() {
		List<String> types = new ArrayList<String>();
		ModelNode triggers = get(DEPLOYMENTCONFIG_TRIGGERS);
		for (ModelNode node : triggers.asList()) {
			types.add(node.get(TYPE).asString());
		}
		return types;
	}

	@Override
	public Collection<IDeploymentTrigger> getTrigger() {
		List<IDeploymentTrigger> types = new ArrayList<>();
		ModelNode triggers = get(DEPLOYMENTCONFIG_TRIGGERS);
		for (ModelNode node : triggers.asList()) {
			switch (node.get(TYPE).asString()) {
				case DeploymentTriggerType.IMAGE_CHANGE:
					types.add(new ImageChangeTrigger(node, propertyKeys));
				case DeploymentTriggerType.CONFIG_CHANGE:
				default:
			}
			types.add(new DeploymentTrigger(node, propertyKeys));
		}
		return types;
	}

	@Override
	public int getReplicas() {
		return asInt(DEPLOYMENTCONFIG_REPLICAS);
	}

	@Override
	public void setReplicas(int replicas) {
		set(DEPLOYMENTCONFIG_REPLICAS, replicas);
	}

	@Override
	public IDeploymentTrigger addTrigger(String type) {
		ModelNode triggers = get(DEPLOYMENTCONFIG_TRIGGERS);
		ModelNode triggerNode = triggers.add();
		triggerNode.get(TYPE).set(type);
		switch (type) {
			case DeploymentTriggerType.IMAGE_CHANGE:
				return new ImageChangeTrigger(triggerNode, propertyKeys);
			case DeploymentTriggerType.CONFIG_CHANGE:
			default:
		}
		return new DeploymentTrigger(triggerNode, propertyKeys);
	}

	@Override
	public String getDeploymentStrategyType() {
		return asString(DEPLOYMENTCONFIG_STRATEGY);
	}

	@Override
	public int getLatestVersion() {
		return get(DEPLOYMENTCONFIG_STATUS_LATEST_VERSION).asInt(0);
	}

	@Override
	public void setLatestVersion(int i) {
		set(DEPLOYMENTCONFIG_STATUS_LATEST_VERSION, i);
	}

}
