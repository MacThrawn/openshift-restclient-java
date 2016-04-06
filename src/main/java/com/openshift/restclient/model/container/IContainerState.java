package com.openshift.restclient.model.container;

import com.openshift.internal.restclient.model.container.ContainerStateRunning;
import com.openshift.internal.restclient.model.container.ContainerStateTerminated;
import com.openshift.internal.restclient.model.container.ContainerStateWaiting;

public interface IContainerState {

	boolean isRunning();

	boolean isWaiting();

	boolean isTerminated();

	ContainerStateTerminated getTerminated();

	ContainerStateRunning getRunning();

	void setWaiting(ContainerStateWaiting waiting);

	ContainerStateWaiting getWaiting();

}
