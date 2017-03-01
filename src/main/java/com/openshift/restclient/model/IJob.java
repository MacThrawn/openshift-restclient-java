package com.openshift.restclient.model;

import java.util.Collection;

import com.openshift.internal.restclient.model.job.JobStatus;

public interface IJob extends IResource {

    Collection<String> getImages();
    JobStatus getStatus();

}
