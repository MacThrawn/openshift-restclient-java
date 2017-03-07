package com.openshift.restclient.model;

import java.util.Collection;

public interface IJob extends IResource {

    Collection<String> getImages();

    /**
     * Get the transient template values of the Job container souce images.
     * 
     * @return the container parameter 'sourceImage' for all containers in the job
     */
    Collection<String> getSourceImages();
    /**
     * @return the JSON representing the job status
     */
    String getStatus();

}
