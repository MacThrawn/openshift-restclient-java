package com.openshift.internal.restclient.model.container;

import com.openshift.restclient.model.container.IContainerStateWaiting;

public class ContainerStateWaiting implements IContainerStateWaiting {

	private String reason;
	private String message;

	@Override
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
