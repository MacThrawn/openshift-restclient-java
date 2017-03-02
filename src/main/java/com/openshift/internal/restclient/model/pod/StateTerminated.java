package com.openshift.internal.restclient.model.pod;

public class StateTerminated {

    private State terminated;

    public State getTerminated() {
        return terminated;
    }

    public void setTerminated(State terminated) {
        this.terminated = terminated;
    }
}
