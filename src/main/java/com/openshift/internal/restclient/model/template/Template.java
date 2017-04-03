/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.internal.restclient.model.Job;
import com.openshift.internal.restclient.model.KubernetesResource;
import com.openshift.internal.restclient.model.Pod;
import com.openshift.restclient.IClient;
import com.openshift.restclient.IResourceFactory;
import com.openshift.restclient.OpenShiftException;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.capability.CapabilityVisitor;
import com.openshift.restclient.capability.resources.ITags;
import com.openshift.restclient.model.IResource;
import com.openshift.restclient.model.template.IParameter;
import com.openshift.restclient.model.template.ITemplate;

/**
 * @author Jeff Cantrill
 */
public class Template extends KubernetesResource implements ITemplate {

    private static final String TEMPLATE_PARAMETERS = "parameters";
    private static final String TEMPLATE_OBJECT_LABELS = "labels";
    private static final String JOB_CHILD_OBJECT_TEMPLATE_LABELS = "spec.template.metadata.labels";
    private static final String POD_CHILD_OBJECT_TEMPLATE_LABELS = "metadata.labels";

    public Template(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
        super(node, client, overrideProperties);
    }

    @Override
    public Map<String, String> getObjectLabels() {
        return Collections.unmodifiableMap(asMap(TEMPLATE_OBJECT_LABELS));
    }

    @Override
    public void addObjectLabel(String key, String value) {
        ModelNode labels = getNode().get(getPath(TEMPLATE_OBJECT_LABELS));
        labels.get(key).set(value);
    }

    @Override
    public Map<String, IParameter> getParameters() {
        Map<String, IParameter> params = new HashMap<String, IParameter>();
        ModelNode modelNode = get(TEMPLATE_PARAMETERS);
        if (modelNode.isDefined()) {
            Collection<ModelNode> nodes = modelNode.asList();
            for (ModelNode node : nodes) {
                Parameter p = new Parameter(node);
                params.put(p.getName(), p);
            }
        }
        return params;
    }

    @Override
    public Collection<IResource> getItems() {
        Collection<ModelNode> nodes = get(OBJECTS).asList();
        List<IResource> resources = new ArrayList<IResource>(nodes.size());
        IResourceFactory factory = getClient().getResourceFactory();
        if (factory != null) {
            for (ModelNode node : nodes) {
                resources.add(factory.create(node.toJSONString(true)));
            }
        }
        return resources;
    }

    @Override
    public void updateParameterValues(Collection<IParameter> parameters) {
        Map<String, IParameter> actuals = getParameters();
        for (IParameter param : parameters) {
            if (actuals.containsKey(param.getName())) {
                actuals.get(param.getName()).setValue(param.getValue());
            }
        }
    }

    /**
     * Returns <code>true</code> if this template contains the given text in
     * name or tags.
     * 
     * @param filterText
     * @param template
     * @return
     */
    @Override
    public boolean isMatching(final String text) {
        if (StringUtils.isBlank(text)) {
            return true;
        }
        if (getName().contains(text)) {
            return true;
        }

        return accept(new CapabilityVisitor<ITags, Boolean>() {
            @Override
            public Boolean visit(ITags capability) {
                for (String tag : capability.getTags()) {
                    if (tag.contains(text)) {
                        return true;
                    }
                }
                return false;
            }
        }, false);
    }

    @Override
    public void addChildObjectLabels(String key, String value) {
        for (ModelNode node : get(OBJECTS).asList()) {
            String kind = node.get("kind").asString();
            if (kind.equals(ResourceKind.JOB)) {
                ModelNode labels = node.get(getPath(JOB_CHILD_OBJECT_TEMPLATE_LABELS));
                labels.get(key).set(value);
            } else if (kind.equals(ResourceKind.POD)) {
                ModelNode labels = node.get(getPath(POD_CHILD_OBJECT_TEMPLATE_LABELS));
                labels.get(key).set(value);
            }
        }
    }

    @Override
    public void setFirstContainerImage(String kind, String image) {
        if (ResourceKind.POD.equals(kind)) {
            ModelNode node = get(OBJECTS).asList().iterator().next();
            ModelNode containerNode = get(node, Pod.POD_TEMPLATE_CONTAINERS);
            if (containerNode.getType() != ModelType.LIST) {
                throw new OpenShiftException("Failed to set Pod Container image.");
            }
            set(containerNode.asList().iterator().next(), Pod.IMAGE, image);
        } else if (ResourceKind.JOB.equals(kind)) {
            ModelNode node = get(OBJECTS).asList().iterator().next();
            ModelNode containerNode = get(node, Job.JOB_TEMPLATE_CONTAINERS);
            if (containerNode.getType() != ModelType.LIST) {
                throw new OpenShiftException("Failed to set Job Container image.");
            }
            set(containerNode.asList().iterator().next(), Job.IMAGE, image);
        }
        // throw exception?
    }

}
