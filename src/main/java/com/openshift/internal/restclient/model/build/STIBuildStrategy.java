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
import com.openshift.restclient.model.build.ISTIBuildStrategy;

/**
 * @author Jeff Cantrill
 */
@Deprecated
public class STIBuildStrategy extends BaseBuildStrategy implements ISTIBuildStrategy {

	private final String scriptsLocation;
	private final boolean incremental;

	public STIBuildStrategy(String scriptsLocation, boolean incremental, Map<String, String> envVars) {
		super(envVars);
		this.scriptsLocation = scriptsLocation;
		this.incremental = incremental;
	}

	@Override
	public String getType() {
		return BuildStrategyType.STI;
	}

	@Override
	public String getScriptsLocation() {
		return scriptsLocation;
	}

	@Override
	public boolean incremental() {
		return incremental;
	}

	@Override
	public boolean forceClean() {
		return !incremental;
	}

}
