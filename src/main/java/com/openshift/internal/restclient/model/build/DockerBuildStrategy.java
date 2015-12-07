/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model.build;

import java.util.Map;

import com.openshift.restclient.model.build.BuildStrategyType;
import com.openshift.restclient.model.build.IDockerBuildStrategy;

/**
 * @author Jeff Cantrill
 */
public class DockerBuildStrategy extends BaseBuildStrategy implements IDockerBuildStrategy {

	private final boolean noCache;

	private final String contextDir;

	public DockerBuildStrategy(String contextDir, boolean noCache, Map<String, String> env) {
		super(env);
		this.contextDir = contextDir;
		this.noCache = noCache;
	}

	@Override
	public String getType() {
		return BuildStrategyType.DOCKER;
	}

	@Override
	public String getContextDir() {
		return contextDir;
	}

	@Override
	public boolean isNoCache() {
		// TODO Auto-generated method stub
		return noCache;
	}

}
