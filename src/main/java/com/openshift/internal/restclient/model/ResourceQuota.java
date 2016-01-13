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
import com.openshift.restclient.model.IResourceQuota;
import com.openshift.restclient.model.IResourceQuotaItem;

public class ResourceQuota extends KubernetesResource implements IResourceQuota {

	private static final String STATUS_HARD = "status.hard";
	private static final String STATUS_USED = "status.used";

	public ResourceQuota(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IResourceQuotaItem getHard() {
		return create(get(STATUS_HARD));
	}

	@Override
	public IResourceQuotaItem getUsed() {
		return create(get(STATUS_USED));
	}

	private IResourceQuotaItem create(ModelNode modelNode) {
		return new ResourceQuotaItem(modelNode);
	}

}
