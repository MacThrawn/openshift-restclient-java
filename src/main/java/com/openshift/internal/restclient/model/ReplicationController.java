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

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IReplicationController;

/**
 * @author Jeff Cantrill
 */
public class ReplicationController extends PodTemplate implements IReplicationController {

	private static final String REPLICATION_CONTROLLER_REPLICA_COUNT = "spec.replicas";
	private static final String REPLICATION_CONTROLLER_REPLICA_SELECTOR = "spec.selector";
	private static final String REPLICATION_CONTROLLER_CURRENT_REPLICA_COUNT = "status.replicas";

	public ReplicationController(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
	}

	@Override
	public int getDesiredReplicaCount() {
		return asInt(REPLICATION_CONTROLLER_REPLICA_COUNT);
	}

	@Override
	public Map<String, String> getReplicaSelector() {
		return asMap(REPLICATION_CONTROLLER_REPLICA_SELECTOR);
	}

	@Override
	public int getCurrentReplicaCount() {
		return asInt(REPLICATION_CONTROLLER_CURRENT_REPLICA_COUNT);
	}

}
