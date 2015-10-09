/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.restclient.model;

import java.util.Collection;
import java.util.Map;

import com.openshift.restclient.model.deploy.IDeploymentTrigger;

/**
 * @author Jeff Cantrill
 */
public interface IDeploymentConfig extends IResource, IPodTemplate {

	/**
	 * Returns the number of replicas to be created by the replication
	 * controller generated from this deployment config
	 *
	 * @return
	 */
	int getReplicas();

	void setReplicas(int replicas);

	/**
	 * Returns the replica selector to be used by the replication
	 * controller generated from this deployment config
	 *
	 * @return java.util.Map<String, String>
	 */
	Map<String, String> getReplicaSelector();

	/**
	 * Set the selector by completely replacing the values
	 * that were there before
	 *
	 * @param selector
	 */
	void setReplicaSelector(Map<String, String> selector);

	/**
	 * Convenience method to set the selector when there
	 * is a single key/value pair
	 *
	 * @param key
	 * @param value
	 */
	void setReplicaSelector(String key, String value);

	/**
	 * Get the list of deployment triggers
	 *
	 * @return a collection of trigger types
	 */
	Collection<String> getTriggerTypes();

	/**
	 * Convenience method to get the deployment
	 * strategy type
	 *
	 * @return the type as a string
	 */
	String getDeploymentStrategyType();

	/**
	 * Add a trigger of the given type
	 * or null if the type is unrecognized
	 *
	 * @param type
	 * @return
	 */
	IDeploymentTrigger addTrigger(String type);

	/**
	 * Read the latest version of this deployment configuration
	 *
	 * @return
	 */
	int getLatestVersion();

	/**
	 * Sets the latest version of this deployment configuration
	 *
	 * @param i
	 */
	void setLatestVersion(int i);
}
