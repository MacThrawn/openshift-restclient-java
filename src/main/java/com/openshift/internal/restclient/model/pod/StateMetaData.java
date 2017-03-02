package com.openshift.internal.restclient.model.pod;

public abstract class StateMetaData {

    private String name;
    private int restartCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestartCount() {
        return restartCount;
    }

    public void setRestartCount(int restartCount) {
        this.restartCount = restartCount;
    }
}
