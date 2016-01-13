package com.openshift.restclient.model;

public interface IInvolvedObject {

	String getKind();

	String getName();

	String getNamespace();

	String getResourceVersion();
}
