package com.openshift.internal.restclient.model.pod;

public class PodTerminated extends StateMetaData {

    private StateTerminated state;
    private StateTerminated lastState;

    public StateTerminated getState() {
        return state;
    }

    public void setState(StateTerminated state) {
        this.state = state;
    }

    public StateTerminated getLastState() {
        return lastState;
    }

    public void setLastState(StateTerminated lastState) {
        this.lastState = lastState;
    }
}
