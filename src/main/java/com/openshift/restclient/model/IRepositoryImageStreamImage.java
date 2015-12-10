package com.openshift.restclient.model;

import java.util.Date;

public interface IRepositoryImageStreamImage {

	Date getCreated();

	String getDockerImageReference();

	String getImage();
}
