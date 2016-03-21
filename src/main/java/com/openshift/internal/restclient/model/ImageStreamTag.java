/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IImageStreamTag;

/**
 * @author Jeff Cantrill
 */
public class ImageStreamTag extends KubernetesResource implements IImageStreamTag {

	public ImageStreamTag() {
		this(new ModelNode(), null, null);
	}

	public ImageStreamTag(ModelNode node, IClient client, Map<String, String[]> propertyKeys) {
		super(node, client, propertyKeys);
	}

	@Override
	public Date getCommittedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
		String dateAsString = getImageLabels().get("io.openshift.build.commit.date");
		try {
			return sdf.parse(dateAsString);
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public String getCommitterName() {
		return getImageLabels().get("io.openshift.build.commit.author");
	}

	@Override
	public String getCommitId() {
		return getImageLabels().get("io.openshift.build.commit.id");
	}

	@Override
	public String getCommitMessage() {
		return getImageLabels().get("io.openshift.build.commit.message");
	}

	@Override
	public String getCommitRef() {
		return getImageLabels().get("io.openshift.build.commit.ref");
	}

	@Override
	public String getImageName() {
		return asString("image.metadata.name");
	}

	private Map<String, String> getImageLabels() {
		return asMap("image.dockerImageMetadata.Config.Labels");
	}

	public static void main(String[] args) throws ParseException {
		//String date = "Wed Mar 16 10:41:41 2016 +0100";
		String date = "Wed Mar 16 10:41:41 2016 +0100";
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
		System.out.println(sdf.parse(date));
	}
}
