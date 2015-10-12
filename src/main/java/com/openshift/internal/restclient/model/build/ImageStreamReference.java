package com.openshift.internal.restclient.model.build;

import com.openshift.internal.restclient.model.ObjectReference;
import com.openshift.restclient.ResourceKind;

public class ImageStreamReference extends ObjectReference {

	private final String tag;

	public ImageStreamReference(String name, String namespace, String tag) {
		super(ResourceKind.IMAGE_STREAM, name, namespace);
		this.tag = tag;
	}

	public ImageStreamReference(String name, String tag) {
		this(name, null, tag);
	}

	@Override
	public String getName() {
		if (tag != null) {
			return super.getName() + ":" + tag;
		} else {
			return super.getName();
		}
	}

	public String getTag() {
		return tag;
	}

}
