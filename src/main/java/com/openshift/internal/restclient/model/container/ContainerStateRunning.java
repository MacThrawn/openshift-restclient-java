package com.openshift.internal.restclient.model.container;

import java.util.Date;

import com.openshift.restclient.model.container.IContainerStateRunning;

public class ContainerStateRunning implements IContainerStateRunning {

	private Date startedAt;

	@Override
	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

}
