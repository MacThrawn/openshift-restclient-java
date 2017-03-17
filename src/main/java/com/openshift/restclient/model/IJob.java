package com.openshift.restclient.model;

import java.util.Collection;

public interface IJob extends IResource {

    Collection<String> getImages();

    /**
     * Get the transient template values of the Job container origin images.
     * 
     * @return the container parameter 'originImage' for all containers in the job
     */
    Collection<String> getOriginImages();

    /**
     * @return the JSON representing the job status
     */
    String getStatus();

    /**
     * Retrieve the maximum failure runs allowed for any container.
     * 
     * @return the max reruns allowed
     */
    Integer getMaxRestartCount();

}
