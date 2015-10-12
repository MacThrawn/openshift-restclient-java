package com.openshift.restclient.model;

public interface IObjectReference {

	public abstract String getKind();

	public abstract String getName();

	public abstract String getNamespace();

}
