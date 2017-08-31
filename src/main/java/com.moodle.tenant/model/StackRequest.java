package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class StackRequest {
    private String stackName;
    private String clientName;
    private String vpcId;

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    @Override
    public String toString() {
        return "StackRequest{" +
                "stackName='" + stackName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vpcId='" + vpcId + '\'' +
                '}';
    }
}
