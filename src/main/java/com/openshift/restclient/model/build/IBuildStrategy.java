/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient.model.build;

import java.util.Map;

import com.openshift.internal.restclient.model.ImageStreamReferenceTag;

/**
 * @author Jeff Cantrill
 */
public interface IBuildStrategy {

	/**
	 * The type of build Strategy
	 *
	 * @return {@link BuildStrategyType}
	 */
	String getType();

	ImageStreamReferenceTag getFrom();

	void setFrom(ImageStreamReferenceTag from);

	Map<String, String> getEnvironmentVariables();
}
