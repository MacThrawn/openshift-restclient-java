package com.openshift.internal.restclient.model.container;

import com.openshift.restclient.model.container.IContainerState;
import com.openshift.restclient.model.container.IContainerStatus;

public class ContainerStatus implements IContainerStatus {

	private final String name;
	private IContainerState state;
	private IContainerState lastState;
	private final int restartCount;
	private final boolean ready;

	public ContainerStatus(String name, boolean ready, int restartCount) {
		this.name = name;
		this.ready = ready;
		this.restartCount = restartCount;
	}

	@Override
	public IContainerState getState() {
		return state;
	}

	public void setState(IContainerState state) {
		this.state = state;
	}

	@Override
	public IContainerState getLastState() {
		return lastState;
	}

	public void setLastState(IContainerState lastState) {
		this.lastState = lastState;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isReady() {
		return ready;
	}

	@Override
	public int getRestartCount() {
		return restartCount;
	}

}
