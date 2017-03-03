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
import java.util.List;
import java.util.Set;

import com.openshift.restclient.model.container.IContainerStatus;

/**
 * @author Jeff Cantrill
 */
public interface IPod extends IResource {

	/**
	 * Gets the IP of the Pod
	 *
	 * @return
	 */
	String getIP();

	/**
	 * Gets the name of the host on which
	 * the pod is running
	 *
	 * @return
	 */
	String getHost();

	/**
	 * Gets the collection of image names
	 * for the pod containers
	 *
	 * @return
	 */
	Collection<String> getImages();

	/**
	 * Gets the status of the pod
	 *
	 * @return
	 */
	String getStatus();

	/**
	 * @return a list of JSON strings containing the pod states
	 */
	List<String> getPodContainerStates();

	/**
	 * Reads the Ready condition
	 *
	 * @return
	 */
	boolean isReady();

	/**
	 * Reads the POD status conditions
	 *
	 * @return
	 */
	Collection<IPodCondition> getPodCondtions();

	/**
	 * Find the Ready condition
	 *
	 * @return the ready condition or null
	 */
	IPodCondition getPodConditionReady();

	/**
	 * Retrieve the set of ports that the
	 * containers are using
	 */
	Set<IPort> getContainerPorts();

	/**
	 * Read the container statuses
	 * 
	 * @return
	 */
	Collection<IContainerStatus> getContainerStatuses();

	String getLabel(String key);
}
