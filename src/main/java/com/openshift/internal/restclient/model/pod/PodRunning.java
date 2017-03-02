package com.openshift.internal.restclient.model.pod;

/**
 * Model of the kubernetes Pod status.
 * 
 * @author Brian Singer
 */
public class PodRunning extends StateMetaData {

    private StateRunning state;

    public StateRunning getState() {
        return state;
    }

    public void setState(StateRunning state) {
        this.state = state;
    }
}
