package com.openshift.restclient.model;

public interface IResourceQuotaItem {

	long getCpu();

	long getMemory();

	int getPersistentVolumeClaims();

	int getPods();

	int getReplicationControllers();

	int getResourceQuotas();

	int getSecrets();

	int getServices();
}
