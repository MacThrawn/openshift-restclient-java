package com.openshift.restclient.model;

import java.util.Collection;

public interface IJob extends IResource {

    Collection<String> getImages();
    /**
     * @return the JSON representing the job status
     */
    String getStatus();

}
