/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jboss.dmr.ModelNode;

import com.openshift.internal.restclient.capability.CapabilityInitializer;
import com.openshift.internal.restclient.model.build.CustomBuildStrategy;
import com.openshift.internal.restclient.model.build.DockerBuildStrategy;
import com.openshift.internal.restclient.model.build.GitBuildSource;
import com.openshift.internal.restclient.model.build.ImageChangeTrigger;
import com.openshift.internal.restclient.model.build.SourceBuildStrategy;
import com.openshift.internal.restclient.model.build.WebhookTrigger;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.images.DockerImageURI;
import com.openshift.restclient.model.IBuildConfig;
import com.openshift.restclient.model.IObjectReference;
import com.openshift.restclient.model.build.BuildSourceType;
import com.openshift.restclient.model.build.BuildStrategyType;
import com.openshift.restclient.model.build.BuildTriggerType;
import com.openshift.restclient.model.build.IBuildSource;
import com.openshift.restclient.model.build.IBuildStrategy;
import com.openshift.restclient.model.build.IBuildTrigger;
import com.openshift.restclient.model.build.ICustomBuildStrategy;
import com.openshift.restclient.model.build.IDockerBuildStrategy;
import com.openshift.restclient.model.build.IGitBuildSource;
import com.openshift.restclient.model.build.IImageChangeTrigger;
import com.openshift.restclient.model.build.IImageChangeTriggerEmpty;
import com.openshift.restclient.model.build.ISTIBuildStrategy;
import com.openshift.restclient.model.build.IWebhookTrigger;

/**
 * @author Jeff Cantrill
 */
public class BuildConfig extends KubernetesResource implements IBuildConfig {

	private static final String BUILDCONFIG_SOURCE_CONTEXTDIR = "spec.source.contextDir";
	private static final String BUILDCONFIG_SOURCE_TYPE = "spec.source.type";
	private static final String BUILDCONFIG_SOURCE_URI = "spec.source.git.uri";
	private static final String BUILDCONFIG_SOURCE_REF = "spec.source.git.ref";
	private static final String BUILD_CONFIG_SOURCE_SECRET_NAME = "spec.source.sourceSecret.name";
	public static final String BUILDCONFIG_TYPE = "spec.strategy.type";
	private static final String BUILDCONFIG_CUSTOM = "spec.strategy.customStrategy";
	private static final String BUILDCONFIG_CUSTOM_IMAGE = "spec.strategy.customStrategy.image";
	private static final String BUILDCONFIG_CUSTOM_EXPOSEDOCKERSOCKET = "spec.strategy.customStrategy.exposeDockerSocket";
	private static final String BUILDCONFIG_CUSTOM_ENV = "spec.strategy.customStrategy.env";
	private static final String BUILDCONFIG_DOCKER = "spec.strategy.dockerStrategy";
	public static final String BUILDCONFIG_DOCKER_CONTEXTDIR = "spec.strategy.dockerStrategy.contextDir";
	public static final String BUILDCONFIG_DOCKER_NOCACHE = "spec.strategy.dockerStrategy.noCache";
	private static final String BUILDCONFIG_DOCKER_ENV = "spec.strategy.dockerStrategy.env";
	private static final String BUILDCONFIG_OUTPUT_REPO = "spec.output.to.name";
	private static final String BUILDCONFIG_STI = "spec.strategy.sourceStrategy";
	private static final String BUILDCONFIG_STI_IMAGE = "spec.strategy.sourceStrategy.from.name";
	private static final String BUILDCONFIG_STI_SCRIPTS = "spec.strategy.sourceStrategy.scripts";
	private static final String BUILDCONFIG_STI_INCREMENTAL = "spec.strategy.sourceStrategy.incremental";
	private static final String BUILDCONFIG_STI_ENV = "spec.strategy.sourceStrategy.env";
	private static final String BUILDCONFIG_TRIGGERS = "spec.triggers";
	private static final String BUILD_CONFIG_WEBHOOK_GITHUB_SECRET = "github.secret";
	private static final String BUILD_CONFIG_WEBHOOK_GENERIC_SECRET = "generic.secret";
	private static final String BUILD_CONFIG_IMAGECHANGE = "imageChange";
	private static final String BUILD_CONFIG_IMAGECHANGE_IMAGE = "imageChange.image";
	private static final String BUILD_CONFIG_IMAGECHANGE_NAME = "imageChange.from.name";
	private static final String BUILD_CONFIG_IMAGECHANGE_TAG = "imageChange.tag";
	private static final String BUILDCONFIG_OUTPUT_TO = "spec.output.to";
	private static final String BUILDCONFIG_STATUS_LASTVERSION = "status.lastVersion";

	public BuildConfig(ModelNode node, IClient client, Map<String, String[]> overrideProperties) {
		super(node, client, null);
		CapabilityInitializer.initializeCapabilities(getModifiableCapabilities(), this, client);
	}

	@Override
	public List<IBuildTrigger> getBuildTriggers() {
		List<IBuildTrigger> triggers = new ArrayList<IBuildTrigger>();
		List<ModelNode> list = get(BUILDCONFIG_TRIGGERS).asList();
		final String url = getClient() != null ? getClient().getResourceURI(this) : "";
		for (ModelNode node : list) {
			String type = node.get("type").asString();
			switch (type) {
				case BuildTriggerType.generic:
				case BuildTriggerType.GENERIC:
					triggers.add(new WebhookTrigger(BuildTriggerType.GENERIC,
						asString(node, BUILD_CONFIG_WEBHOOK_GENERIC_SECRET), url));
					break;
				case BuildTriggerType.github:
				case BuildTriggerType.GITHUB:
					triggers.add(new WebhookTrigger(BuildTriggerType.GITHUB, asString(node, BUILD_CONFIG_WEBHOOK_GITHUB_SECRET), url));
					break;
				case BuildTriggerType.imageChange:
				case BuildTriggerType.IMAGE_CHANGE:
					triggers.add(new ImageChangeTrigger(BuildTriggerType.IMAGE_CHANGE,
						asString(node, BUILD_CONFIG_IMAGECHANGE_IMAGE),
						asString(node, BUILD_CONFIG_IMAGECHANGE_NAME),
						asString(node, BUILD_CONFIG_IMAGECHANGE_TAG))
						);
					break;
				default:
			}
		}
		return triggers;
	}

	@Override
	public void addBuildTrigger(IBuildTrigger trigger) {
		ModelNode triggers = get(BUILDCONFIG_TRIGGERS);
		ModelNode triggerNode = triggers.add();
		switch (trigger.getType()) {
			case BuildTriggerType.generic:
			case BuildTriggerType.GENERIC:
				if (!(trigger instanceof IWebhookTrigger)) {
					throw new IllegalArgumentException("IBuildTrigger of type generic does not implement IWebhookTrigger");
				}
				IWebhookTrigger generic = (IWebhookTrigger) trigger;
				triggerNode.get(getPath(BUILD_CONFIG_WEBHOOK_GENERIC_SECRET)).set(generic.getSecret());
				break;
			case BuildTriggerType.github:
			case BuildTriggerType.GITHUB:
				if (!(trigger instanceof IWebhookTrigger)) {
					throw new IllegalArgumentException("IBuildTrigger of type github does not implement IWebhookTrigger");
				}
				IWebhookTrigger github = (IWebhookTrigger) trigger;
				triggerNode.get(getPath(BUILD_CONFIG_WEBHOOK_GITHUB_SECRET)).set(github.getSecret());
				break;
			case BuildTriggerType.imageChange:
			case BuildTriggerType.IMAGE_CHANGE:
				if (trigger instanceof IImageChangeTriggerEmpty) {
					triggerNode.get("imageChange").set("chopsuey", "placeholder");
					break;
				}
				if (!(trigger instanceof IImageChangeTrigger)) {
					throw new IllegalArgumentException("IBuildTrigger of type imageChange does not implement IImageChangeTrigger");
				}

				IImageChangeTrigger image = (IImageChangeTrigger) trigger;
				triggerNode.get(getPath(BUILD_CONFIG_IMAGECHANGE_IMAGE)).set(defaultIfNull(image.getImage()));
				triggerNode.get(getPath(BUILD_CONFIG_IMAGECHANGE_NAME)).set(defaultIfNull(image.getFrom()));
				triggerNode.get(getPath(BUILD_CONFIG_IMAGECHANGE_TAG)).set(StringUtils.defaultIfBlank(image.getTag(), ""));
				break;
		}
		triggerNode.get("type").set(trigger.getType());
	}

	private String defaultIfNull(DockerImageURI uri) {
		if (uri == null) {
			return "";
		}
		return uri.toString();
	}

	@Override
	public String getOutputRepositoryName() {
		return asString(BUILDCONFIG_OUTPUT_REPO);
	}

	@Override
	public String getSourceURI() {
		return asString(BUILDCONFIG_SOURCE_URI);
	}

	@Override
	public String getLastVersion() {
		return asString(BUILDCONFIG_STATUS_LASTVERSION);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBuildSource> T getBuildSource() {
		switch (asString(BUILDCONFIG_SOURCE_TYPE)) {
			case BuildSourceType.GIT:
				return (T) new GitBuildSource(asString(BUILDCONFIG_SOURCE_URI), asString(BUILDCONFIG_SOURCE_REF), asString(BUILDCONFIG_SOURCE_CONTEXTDIR));
			default:
		}
		return null;
	}

	@Override
	public void setBuildSource(IBuildSource source) {
		switch (source.getType()) {
			case BuildSourceType.GIT:
				if (!(source instanceof IGitBuildSource)) {
					throw new IllegalArgumentException("IBuildSource of type Git does not implement IGitBuildSource");
				}
				IGitBuildSource git = (IGitBuildSource) source;
				set(BUILDCONFIG_SOURCE_REF, git.getRef());
				break;
		}
		set(BUILDCONFIG_SOURCE_URI, source.getURI());
		set(BUILDCONFIG_SOURCE_TYPE, source.getType().toString());
		set(BUILDCONFIG_SOURCE_CONTEXTDIR, source.getContextDir());
		set(BUILD_CONFIG_SOURCE_SECRET_NAME, source.getSourceSecret());
	}

	@Override
	public void setBuildStrategy(IBuildStrategy strategy) {
		// Remove other strategies if already set?
		String from = ".from";
		switch (strategy.getType()) {
			case BuildStrategyType.CUSTOM:
				from = BUILDCONFIG_CUSTOM + from;
				if (!(strategy instanceof ICustomBuildStrategy)) {
					throw new IllegalArgumentException("IBuildStrategy of type Custom does not implement ICustomBuildStrategy");
				}
				ICustomBuildStrategy custom = (ICustomBuildStrategy) strategy;
				if (custom.getImage() != null) {
					set(BUILDCONFIG_CUSTOM_IMAGE, custom.getImage().toString());
				}
				set(BUILDCONFIG_CUSTOM_EXPOSEDOCKERSOCKET, custom.exposeDockerSocket());
				if (custom.getEnvironmentVariables() != null) {
					setEnvMap(BUILDCONFIG_CUSTOM_ENV, custom.getEnvironmentVariables());
				}
				break;
			case BuildStrategyType.STI:
			case BuildStrategyType.SOURCE:
				from = BUILDCONFIG_STI + from;
				if (!(strategy instanceof ISTIBuildStrategy)) {
					throw new IllegalArgumentException("IBuildStrategy of type Custom does not implement ISTIBuildStrategy");
				}
				ISTIBuildStrategy sti = (ISTIBuildStrategy) strategy;
				if (sti.getScriptsLocation() != null) {
					set(BUILDCONFIG_STI_SCRIPTS, sti.getScriptsLocation());
				}
				set(BUILDCONFIG_STI_INCREMENTAL, sti.incremental());
				if (sti.getEnvironmentVariables() != null) {
					setEnvMap(BUILDCONFIG_STI_ENV, sti.getEnvironmentVariables());
				}
				break;
			case BuildStrategyType.DOCKER:
				from = BUILDCONFIG_DOCKER + from;
				if (!(strategy instanceof IDockerBuildStrategy)) {
					throw new IllegalArgumentException("IBuildStrategy of type Custom does not implement IDockerBuildStrategy");
				}
				IDockerBuildStrategy docker = (IDockerBuildStrategy) strategy;
				if (docker.getContextDir() != null) {
					set(BUILDCONFIG_DOCKER_CONTEXTDIR, docker.getContextDir());
				}
				set(BUILDCONFIG_DOCKER_NOCACHE, docker.isNoCache());
				break;
		}

		ImageStreamReferenceTag isr = strategy.getFrom();
		if (isr != null) {
			set(from + ".kind", isr.getKind());
			set(from + ".namespace", isr.getNamespace());
			set(from + ".name", isr.getName());
		}
		set(BUILDCONFIG_TYPE, strategy.getType());
	}

	public void setOutput(IObjectReference objectReference) {

		ModelNode output = get(BUILDCONFIG_OUTPUT_TO);
		output.get("kind").set(objectReference.getKind());
		output.get("name").set(objectReference.getName());
		if (objectReference.getNamespace() != null) {
			output.get("namespace").set(objectReference.getNamespace());
		}

	}

	@SuppressWarnings("unchecked")
	public <T extends IObjectReference> T getOutput() {
		ModelNode output = get(BUILDCONFIG_OUTPUT_TO);
		switch (output.get("kind").asString()) {
			case ResourceKind.IMAGE_STREAM_TAG:
				DockerImageURI uri = new DockerImageURI(output.get("name").asString());
				return (T) new ImageStreamReferenceTag(uri.getName(), output.get("namespace").asString(), uri.getTag());
			case "undefined":
				return null;
			default:
				return (T) new ObjectReference(output.get("kind").asString(), output.get("name").asString());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IBuildStrategy> T getBuildStrategy() {
		switch (asString(BUILDCONFIG_TYPE)) {
			case BuildStrategyType.CUSTOM:
				return (T) new CustomBuildStrategy(
					asString(BUILDCONFIG_CUSTOM_IMAGE),
					asBoolean(BUILDCONFIG_CUSTOM_EXPOSEDOCKERSOCKET),
					getEnvMap(BUILDCONFIG_CUSTOM_ENV));
			case BuildStrategyType.STI:
			case BuildStrategyType.SOURCE:
				return (T) new SourceBuildStrategy(
					asString(BUILDCONFIG_STI_SCRIPTS),
					asBoolean(BUILDCONFIG_STI_INCREMENTAL),
					getEnvMap(BUILDCONFIG_STI_ENV));

			case BuildStrategyType.DOCKER:
				return (T) new DockerBuildStrategy(
					asString(BUILDCONFIG_DOCKER_CONTEXTDIR),
					asBoolean(BUILDCONFIG_DOCKER_NOCACHE),
					getEnvMap(BUILDCONFIG_DOCKER_ENV));
			default:
		}
		return null;
	}

}
