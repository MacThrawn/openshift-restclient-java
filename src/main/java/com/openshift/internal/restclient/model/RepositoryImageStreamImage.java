package com.openshift.internal.restclient.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jboss.dmr.ModelNode;

import com.openshift.internal.util.JBossDmrExtentions;
import com.openshift.restclient.model.IRepositoryImageStreamImage;

public class RepositoryImageStreamImage implements IRepositoryImageStreamImage {

	private static final String IMAGESTREAM_IMAGE_CREATED = "created";
	private static final String IMAGESTREAM_IMAGE_REFERENCE = "dockerImageReference";
	private static final String IMAGESTREAM_IMAGE_IMAGE = "image";

	private final ModelNode node;
	private final Map<String, String[]> propertyKeys;

	public RepositoryImageStreamImage(final ModelNode node, final Map<String, String[]> propertyKeys) {
		this.node = node;
		this.propertyKeys = propertyKeys;
	}

	public RepositoryImageStreamImage(final ModelNode node) {
		this(node, new HashMap<>());
	}

	@Override
	public Date getCreated() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			return df.parse(JBossDmrExtentions.asString(node, propertyKeys, IMAGESTREAM_IMAGE_CREATED));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getDockerImageReference() {
		return JBossDmrExtentions.asString(node, propertyKeys, IMAGESTREAM_IMAGE_REFERENCE);
	}

	@Override
	public String getImage() {
		return JBossDmrExtentions.asString(node, propertyKeys, IMAGESTREAM_IMAGE_IMAGE);
	}
}
