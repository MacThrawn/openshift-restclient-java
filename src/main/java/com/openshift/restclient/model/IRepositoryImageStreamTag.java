package com.openshift.restclient.model;

import java.util.List;

public interface IRepositoryImageStreamTag {

	String getTag();

	List<IRepositoryImageStreamImage> getImages();

	IRepositoryImageStreamImage getLatestImage();
}
