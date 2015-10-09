package com.openshift.restclient.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.dmr.ModelNode;

import com.openshift.restclient.images.DockerImageURI;

public interface IPodTemplate {

	/**
	 * Add or update a label to the template spec;
	 *
	 * @param key
	 * @param value
	 */
	void addTemplateLabel(String key, String value);

	/**
	 * Add a container to the pod that will be spun up as
	 * part of this deployment.
	 *
	 * @param name the name of the container
	 * @param tag the docker uri
	 * @param containerPorts the container ports
	 * @param volumes the set of emptyDir volumes to add to the config
	 */
	void addContainer(String name, DockerImageURI tag, Set<IPort> containerPorts, Map<String, String> envVars, List<String> volumes);

	/**
	 * Add a container to the pod that will be spun up as
	 * part of this deployment, defaulting the name to the image name
	 *
	 * @param tag the docker uri
	 * @param containerPorts the container ports
	 */
	void addContainer(DockerImageURI tag, Set<IPort> containerPorts, Map<String, String> envVars);

	/**
	 * Retrieves the list of images deployed in the
	 * pod containers from this controller
	 *
	 * @return
	 */
	Collection<String> getImages();

	/**
	 * Retrieves the list of containers deployed in the pod containers from this template
	 * 
	 * @return
	 */
	List<ModelNode> getContainers();
}
