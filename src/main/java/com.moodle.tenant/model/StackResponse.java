package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class StackResponse {
    private String stackId;

    public StackResponse() {
    }

    public StackResponse(String stackId) {
        this.stackId = stackId;
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }

    @Override
    public String toString() {
        return "Output{" +
                "stackId='" + stackId + '\'' +
                '}';
    }
}
