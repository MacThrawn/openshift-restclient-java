package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IJob;

public class Job extends KubernetesResource implements IJob {

    public static final String JOB_TEMPLATE_CONTAINERS = "spec.template.spec.containers";
    private static final String JOB_TEMPLATE_LABELS = "spec.template.metadata.labels";
    private static final String IMAGE = "image";
    private static final String NAME = "name";

    public Job(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
        super(node, client, overrideProperties);
    }

    @Override
    public void addTemplateLabel(String key, String value) {
        ModelNode labels = get(JOB_TEMPLATE_LABELS);
        labels.get(key).set(value);
    }

    public Collection<String> getImages() {
        ModelNode node = get(JOB_TEMPLATE_CONTAINERS);
        if (node.getType() != ModelType.LIST) {
            return new ArrayList<String>();
        }
        Collection<String> list = new ArrayList<String>();
        for (ModelNode entry : node.asList()) {
            list.add(entry.get(IMAGE).asString());
        }
        return list;
    }

    public List<ModelNode> getContainers() {
        ModelNode node = get(JOB_TEMPLATE_CONTAINERS);
        if (node.getType() != ModelType.LIST) {
            return new ArrayList<ModelNode>();
        }
        return node.asList();
    }

    public void setImage(String containerName, String imageName) {
        for (ModelNode node : getContainers()) {
            if (node.get(NAME).asString().equals(containerName)) {
                node.set(IMAGE, imageName);
            }
        }

    }

}
