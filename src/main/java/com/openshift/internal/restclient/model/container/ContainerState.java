package com.openshift.internal.restclient.model.container;

import com.openshift.restclient.model.container.IContainerState;

public class ContainerState implements IContainerState {

	private ContainerStateWaiting waiting;
	private ContainerStateRunning running;
	private ContainerStateTerminated terminated;

	@Override
	public ContainerStateWaiting getWaiting() {
		return waiting;
	}

	@Override
	public void setWaiting(ContainerStateWaiting waiting) {
		this.waiting = waiting;
	}

	@Override
	public ContainerStateRunning getRunning() {
		return running;
	}

	public void setRunning(ContainerStateRunning running) {
		this.running = running;
	}

	@Override
	public ContainerStateTerminated getTerminated() {
		return terminated;
	}

	public void setTerminated(ContainerStateTerminated terminated) {
		this.terminated = terminated;
	}

	@Override
	public boolean isRunning() {
		return running != null;
	}

	@Override
	public boolean isWaiting() {
		return waiting != null;
	}

	@Override
	public boolean isTerminated() {
		return terminated != null;
	}

}
