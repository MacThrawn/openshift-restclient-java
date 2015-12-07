package com.openshift.internal.restclient.model.build;

import java.util.Map;

import com.openshift.restclient.model.build.IBuildStrategy;

public abstract class BaseBuildStrategy implements IBuildStrategy {

	private ImageStreamReference from;
	private final Map<String, String> env;

	public BaseBuildStrategy(Map<String, String> env) {
		this.env = env;
	}

	@Override
	public ImageStreamReference getFrom() {
		return from;
	}

	@Override
	public Map<String, String> getEnvironmentVariables() {
		return env;
	}

	@Override
	public void setFrom(ImageStreamReference from) {
		this.from = from;
	}
}
