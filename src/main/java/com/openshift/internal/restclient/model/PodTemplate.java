package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;

import com.openshift.internal.restclient.model.volume.EmptyDirVolume;
import com.openshift.restclient.IClient;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IPodTemplate;
import com.openshift.restclient.model.IPort;

public abstract class PodTemplate extends KubernetesResource implements IPodTemplate {

	public static final String POD_TEMPLATE_CONTAINERS = "spec.template.spec.containers";
	private static final String POD_TEMPLATE_LABELS = "spec.template.metadata.labels";
	private static final String VOLUMES = "spec.template.spec.volumes";

	private static final String IMAGE = "image";
	private static final String ENV = "env";

	public PodTemplate(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
		super(node, client, overrideProperties);
	}

	@Override
	public void addTemplateLabel(String key, String value) {
		ModelNode labels = get(POD_TEMPLATE_LABELS);
		labels.get(key).set(value);
	}

	@Override
	public void addContainer(DockerImageURI tag, Set<IPort> containerPorts, Map<String, String> envVars) {
		addContainer(tag.getName(), tag, containerPorts, envVars, new ArrayList<String>());
	}

	@Override
	public void addContainer(String name, DockerImageURI tag, Set<IPort> containerPorts, Map<String, String> envVars, List<String> emptyDirVolumes) {

		ModelNode container = new ModelNode();
		container.get(NAME).set(name); //required?
		container.get(getPath(IMAGE)).set(tag.getUriWithoutHost());

		if (emptyDirVolumes.size() > 0) {
			ModelNode volumeMounts = container.get("volumeMounts");
			ModelNode volumes = get(VOLUMES);
			for (String path : emptyDirVolumes) {
				EmptyDirVolume volume = new EmptyDirVolume(volumes.add());
				final String volName = String.format("%s-%s", name, emptyDirVolumes.indexOf(path) + 1);
				volume.setName(volName);
				ModelNode volMount = volumeMounts.add();
				volMount.get(NAME).set(volName);
				volMount.get("mountPath").set(path);
			}
		}

		ModelNode ports = container.get(getPath(PORTS));
		for (IPort port : containerPorts) {
			ModelNode portNode = ports.add();
			new Port(portNode, port);
		}

		if (!envVars.isEmpty()) {
			ModelNode env = container.get(getPath(ENV));
			for (Entry<String, String> var : envVars.entrySet()) {
				ModelNode varNode = new ModelNode();
				//dont use path here
				varNode.get(NAME).set(var.getKey());
				varNode.get(VALUE).set(var.getValue());
				env.add(varNode);
			}
		}

		get(POD_TEMPLATE_CONTAINERS).add(container);

	}

	@Override
	public Collection<String> getImages() {
		ModelNode node = get(POD_TEMPLATE_CONTAINERS);
		if (node.getType() != ModelType.LIST) {
			return new ArrayList<String>();
		}
		Collection<String> list = new ArrayList<String>();
		for (ModelNode entry : node.asList()) {
			list.add(entry.get("image").asString());
		}
		return list;
	}

	@Override
	public List<ModelNode> getContainers() {
		ModelNode node = get(POD_TEMPLATE_CONTAINERS);
		if (node.getType() != ModelType.LIST) {
			return new ArrayList<ModelNode>();
		}
		return node.asList();
	}

}
