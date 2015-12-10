package com.openshift.internal.restclient.model;

import com.openshift.restclient.ResourceKind;

public class ImageStreamReferenceTag extends ObjectReference {

	private final String tag;

	public ImageStreamReferenceTag(String name, String namespace, String tag) {
		super(ResourceKind.IMAGE_STREAM_TAG, name, namespace);
		this.tag = tag;
	}

	public ImageStreamReferenceTag(String name, String tag) {
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
