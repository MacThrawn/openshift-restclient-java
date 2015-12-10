package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.List;

import com.openshift.restclient.model.IRepositoryImageStreamImage;
import com.openshift.restclient.model.IRepositoryImageStreamTag;

public class RepositoryImageStreamTag implements IRepositoryImageStreamTag {

	private final String tag;
	private final List<IRepositoryImageStreamImage> images = new ArrayList<>();

	public RepositoryImageStreamTag(final String tag) {
		this.tag = tag;
	}

	@Override
	public String getTag() {
		return tag;
	}

	@Override
	public List<IRepositoryImageStreamImage> getImages() {
		return images;
	}

	public void addImage(RepositoryImageStreamImage imageStreamImage) {
		images.add(imageStreamImage);
	}

	@Override
	public IRepositoryImageStreamImage getLatestImage() {
		IRepositoryImageStreamImage latest = null;
		for (IRepositoryImageStreamImage image : images) {
			if (latest == null) {
				latest = image;
			} else {
				if (latest.getCreated().getTime() < image.getCreated().getTime()) {
					latest = image;
				}
			}
		}
		return latest;
	}

}
