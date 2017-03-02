package com.openshift.internal.restclient.model.job;

import java.util.List;

/**
 * Model of the kubernetes Job status.
 * 
 * @author Brian Singer
 */
public class JobStatus {

    private List<State> conditions;
    private String startTime;
    private String completionTime;
    private int succeeded;

    public List<State> getConditions() {
        return conditions;
    }

    public void setConditions(List<State> conditions) {
        this.conditions = conditions;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public int getSucceeded() {
        return succeeded;
    }

    public void setSucceeded(int succeeded) {
        this.succeeded = succeeded;
    }
}
