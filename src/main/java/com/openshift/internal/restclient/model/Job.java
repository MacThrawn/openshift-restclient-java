package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.restclient.IClient;
import com.openshift.restclient.model.IJob;

public class Job extends KubernetesResource implements IJob {

    public static final String JOB_TEMPLATE_CONTAINERS = "spec.template.spec.containers";
    private static final String IMAGE = "image";

    public Job(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
        super(node, client, overrideProperties);
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

}
