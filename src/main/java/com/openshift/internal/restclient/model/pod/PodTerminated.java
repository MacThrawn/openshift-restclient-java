package com.openshift.internal.restclient.model.pod;

public class PodTerminated extends StateMetaData {

    private StateTerminated state;

    public StateTerminated getState() {
        return state;
    }

    public void setState(StateTerminated state) {
        this.state = state;
    }
}
