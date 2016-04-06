package com.openshift.restclient.model.container;

public interface IContainerStatus {

	int getRestartCount();

	boolean isReady();

	String getName();

	IContainerState getLastState();

	IContainerState getState();

}
