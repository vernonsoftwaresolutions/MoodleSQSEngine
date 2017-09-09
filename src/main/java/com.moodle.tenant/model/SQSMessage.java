package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class SQSMessage {
    private String stackName;
    private String clientName;
    private String vpcId;
    private String hostedZoneName;
    private Integer priority;

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

    public String getHostedZoneName() {
        return hostedZoneName;
    }

    public void setHostedZoneName(String hostedZoneName) {
        this.hostedZoneName = hostedZoneName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "SQSMessage{" +
                "stackName='" + stackName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vpcId='" + vpcId + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
