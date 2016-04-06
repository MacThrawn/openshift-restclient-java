package com.openshift.internal.restclient.model.container;

import java.util.Date;

import com.openshift.restclient.model.container.IContainerStateTerminated;

public class ContainerStateTerminated extends ContainerStateWaiting implements IContainerStateTerminated {

	private final int exitCode;
	private Date startedAt;
	private Date finishedAt;

	public ContainerStateTerminated(int exitCode) {
		super();
		this.exitCode = exitCode;
	}

	@Override
	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	@Override
	public Date getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}

	@Override
	public int getExitCode() {
		return exitCode;
	}

}
