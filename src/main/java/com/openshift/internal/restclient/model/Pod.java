/*******************************************************************************
 * Copyright (c) 2014-2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import static com.openshift.internal.restclient.capability.CapabilityInitializer.initializeCapabilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.internal.restclient.model.container.ContainerState;
import com.openshift.internal.restclient.model.container.ContainerStateRunning;
import com.openshift.internal.restclient.model.container.ContainerStateTerminated;
import com.openshift.internal.restclient.model.container.ContainerStateWaiting;
import com.openshift.internal.restclient.model.container.ContainerStatus;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IPod;
import com.openshift.restclient.model.IPodCondition;
import com.openshift.restclient.model.IPort;
import com.openshift.restclient.model.container.IContainerState;
import com.openshift.restclient.model.container.IContainerStatus;

/**
 * @author Jeff Cantrill
 */
public class Pod extends KubernetesResource implements IPod {

	private static final String POD_IP = "status.podIP";
	private static final String POD_HOST = "status.hostIP";
	private static final String POD_STATUS = "status.phase";
	private static final String POD_CONTAINERS = "spec.containers";
	private static final String POD_CONDITIONS = "status.conditions";
	private static final String POD_CONTAINER_STATUSES = "status.containerStatuses";
	private static final String POD_LABELS = "metadata.labels";

	public Pod(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
		initializeCapabilities(getModifiableCapabilities(), this, client);
	}

	@Override
	public String getIP() {
		return asString(POD_IP);
	}

	@Override
	public String getHost() {
		return asString(POD_HOST);
	}

	@Override
	public Collection<String> getImages() {
		Collection<String> images = new ArrayList<String>();
		ModelNode node = get(POD_CONTAINERS);
		if (node.getType() != ModelType.LIST) {
			return images;
		}
		for (ModelNode entry : node.asList()) {
			images.add(entry.get("image").asString());
		}
		return images;
	}

	@Override
	public String getStatus() {
		return asString(POD_STATUS);
	}

	@Override
	public boolean isReady() {
		IPodCondition condition = getPodConditionReady();
		if (condition != null) {
			return condition.getStatus();
		}
		return false;
	}

	@Override
	public IPodCondition getPodConditionReady() {
		Collection<IPodCondition> conditions = getPodCondtions();
		for (IPodCondition condition : conditions) {
			if ("Ready".equalsIgnoreCase(condition.getType())) {
				return condition;
			}
		}
		return null;
	}

	@Override
	public Collection<IPodCondition> getPodCondtions() {
		Collection<IPodCondition> result = new ArrayList<>();
		ModelNode conditions = get(POD_CONDITIONS);
		if (conditions != null && conditions.getType() == ModelType.LIST) {
			for (ModelNode condition : conditions.asList()) {
				PodCondition podCondition = new PodCondition(condition.get("type").asString(), condition.get("status").asBoolean());
				if (condition.get("message").isDefined()) {
					podCondition.setMessage(condition.get("message").asString());
				}
				if (condition.get("reason").isDefined()) {
					podCondition.setReason(condition.get("reason").asString());
				}
				if (condition.get("lastTransitionTime").isDefined()) {
					podCondition.setLastTransitionTime(parseDate(condition.get("lastTransitionTime").asString()));
				}
				result.add(podCondition);
			}
		}
		return result;
	}

	@Override
	public Collection<IContainerStatus> getContainerStatuses() {
		Collection<IContainerStatus> result = new ArrayList<>();
		ModelNode containerStatuses = get(POD_CONTAINER_STATUSES);
		if (containerStatuses != null && containerStatuses.getType() == ModelType.LIST) {
			for (ModelNode containerStatus : containerStatuses.asList()) {
				ContainerStatus cs = new ContainerStatus(containerStatus.get("name").asString(),
					containerStatus.get("ready").asBoolean(), containerStatus.get("restartCount").asInt());

				cs.setState(extractContainerState(containerStatus.get("state")));
				cs.setLastState(extractContainerState(containerStatus.get("lastState")));
				result.add(cs);
			}
		}
		return result;
	}

	private IContainerState extractContainerState(ModelNode containerState) {
		ContainerState state = new ContainerState();
		if (containerState.hasDefined("running")) {
			ModelNode runningState = containerState.get("running");
			ContainerStateRunning running = new ContainerStateRunning();
			if (runningState.hasDefined("startedAt")) {
				running.setStartedAt(parseDate(runningState.get("startedAt").asString()));
			}
			state.setRunning(running);

		} else if (containerState.hasDefined("waiting")) {
			ModelNode waitingState = containerState.get("waiting");
			ContainerStateWaiting waiting = new ContainerStateWaiting();
			if (waitingState.hasDefined("reason")) {
				waiting.setReason(waitingState.get("reason").asString());
			}
			if (waitingState.hasDefined("message")) {
				waiting.setMessage(waitingState.get("message").asString());
			}

			state.setWaiting(waiting);
		} else if (containerState.hasDefined("terminated")) {
			ModelNode terminatedState = containerState.get("terminated");
			ContainerStateTerminated terminated = new ContainerStateTerminated(terminatedState.get("exitCode").asInt());

			if (terminatedState.hasDefined("reason")) {
				terminated.setReason(terminatedState.get("reason").asString());
			}
			if (terminatedState.hasDefined("message")) {
				terminated.setMessage(terminatedState.get("message").asString());
			}
			if (terminatedState.hasDefined("startedAt")) {
				terminated.setStartedAt(parseDate(terminatedState.get("startedAt").asString()));
			}
			if (terminatedState.hasDefined("finishedAt")) {
				terminated.setFinishedAt(parseDate(terminatedState.get("finishedAt").asString()));
			}

			state.setTerminated(terminated);
		}
		return state;
	}

	@Override
	public Set<IPort> getContainerPorts() {
		Set<IPort> ports = new HashSet<IPort>();
		ModelNode node = get(POD_CONTAINERS);
		if (node.getType() == ModelType.LIST) {
			for (ModelNode container : node.asList()) {
				ModelNode containerPorts = container.get(getPath(PORTS));
				if (containerPorts.getType() == ModelType.LIST) {
					for (ModelNode portNode : containerPorts.asList()) {
						ports.add(new Port(portNode));
					}
				}
			}
		}
		return Collections.unmodifiableSet(ports);
	}

    @Override
    public String getLabel(String key) {
        return get(POD_LABELS).get(key).asString();
    }

    @Override
    public List<String> getPodContainerStates() {
        List<ModelNode> statuses = get(POD_CONTAINER_STATUSES).asList();
        List<String> states = new ArrayList<>();
        for (ModelNode status : statuses) {
            states.add(status.toJSONString(false));
        }
        return states;
    }

}
