/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.internal.restclient.capability.CapabilityInitializer;
import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IBuild;

/**
 * @author Jeff Cantrill
 */
public class Build extends KubernetesResource implements IBuild {

	private static final String BUILD_MESSAGE = "status.message";
	private static final String BUILD_PODNAME = "podName";
	private static final String BUILD_STATUS = "status.phase";
	private static final String GIT = "spec.revision.git";
	private static final String GIT_COMMIT = GIT + ".commit";
	private static final String GIT_COMMITTER_NAME = GIT + ".committer.name";
	private static final String GIT_COMMITTER_EMAIL = GIT + ".committer.email";
	private static final String GIT_MESSAGE = GIT + ".message";

	private static final String SOURCE = "spec.source";
	private static final String SOURCE_GIT_URI = SOURCE + ".git.uri";
	private static final String SOURCE_GIT_REF = SOURCE + ".git.ref";

	public Build(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
		CapabilityInitializer.initializeCapabilities(getModifiableCapabilities(), this, client);
	}

	@Override
	public String getStatus() {
		return asString(BUILD_STATUS);
	}

	@Override
	public String getMessage() {
		return asString(BUILD_MESSAGE);
	}

	@Override
	public String getPodName() {
		return asString(BUILD_PODNAME);
	}

	@Override
	public String getGitCommit() {
		return asString(GIT_COMMIT);
	}

	@Override
	public String getGitCommitterName() {
		return asString(GIT_COMMITTER_NAME);
	}

	@Override
	public String getGitCommitterEmail() {
		return asString(GIT_COMMITTER_EMAIL);
	}

	@Override
	public String getGitMessage() {
		return asString(GIT_MESSAGE);
	}

	@Override
	public String getSourceGitUri() {
		return asString(SOURCE_GIT_URI);
	}

	@Override
	public String getSourceGitRef() {
		return asString(SOURCE_GIT_REF);
	}

}
