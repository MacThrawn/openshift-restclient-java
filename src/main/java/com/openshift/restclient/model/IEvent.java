/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package com.openshift.restclient.model;

/**
 * @author jeff.cantrill
 */
public interface IEvent extends IResource {

	IInvolvedObject getInvolvedObject();

	String getSourceComponent();

	String getReason();

	String getMessage();

	int getCount();

	String getUid();

}
