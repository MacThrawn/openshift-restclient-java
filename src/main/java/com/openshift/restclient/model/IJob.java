package com.openshift.restclient.model;

import java.util.Collection;
import java.util.List;

import org.jboss.dmr.ModelNode;

public interface IJob extends IResource {

    /**
     * Add or update a label to the template spec;
     *
     * @param key
     * @param value
     */
    void addTemplateLabel(String key, String value);
    Collection<String> getImages();
    List<ModelNode> getContainers();
    void setImage(String containerName, String imageName);
}
