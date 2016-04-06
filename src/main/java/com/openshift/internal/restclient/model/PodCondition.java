package com.openshift.internal.restclient.model;

import java.util.Date;

import com.openshift.restclient.model.IPodCondition;

public class PodCondition implements IPodCondition {

	private final String type;
	private final boolean status;

	private Date lastTransitionTime;
	private String reason;
	private String message;

	public PodCondition(String type, boolean status) {
		this.type = type;
		this.status = status;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public boolean getStatus() {
		return status;
	}

	@Override
	public Date getLastTransitionTime() {
		return lastTransitionTime;
	}

	@Override
	public String getReason() {
		return reason;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setLastTransitionTime(Date lastTransitionTime) {
		this.lastTransitionTime = lastTransitionTime;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
