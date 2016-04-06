package com.openshift.restclient.model.container;

import java.util.Date;

public interface IContainerStateTerminated extends IContainerStateWaiting, IContainerStateRunning {

	public Date getFinishedAt();

	public int getExitCode();

}
