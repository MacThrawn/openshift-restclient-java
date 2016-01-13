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
package com.openshift.internal.restclient.model;

import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IEvent;
import com.openshift.restclient.model.IInvolvedObject;

/**
 * @author jeff.cantrill
 */
public class KubernetesEvent extends KubernetesResource implements IEvent {

	private static final String INVOLVED_OBJECT = "involvedObject";
	private static final String REASON = "reason";
	private static final String MESSAGE = "message";
	private static final String SOURCE_COMPONENT = "source.component";
	private static final String COUNT = "count";

	public KubernetesEvent(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
	}

	@Override
	public IInvolvedObject getInvolvedObject() {
		return new InvolvedObject(get(INVOLVED_OBJECT));
	}

	@Override
	public String getSourceComponent() {
		return get(SOURCE_COMPONENT).asString();
	}

	@Override
	public String getReason() {
		return get(REASON).asString();
	}

	@Override
	public String getMessage() {
		return get(MESSAGE).asString();
	}

	@Override
	public int getCount() {
		return get(COUNT).asInt();
	}

}
