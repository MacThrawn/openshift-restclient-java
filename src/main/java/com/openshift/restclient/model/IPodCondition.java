package com.openshift.restclient.model;

import java.util.Date;

public interface IPodCondition {

	String getType();

	boolean getStatus();

	Date getLastTransitionTime();

	String getReason();

	String getMessage();

}
