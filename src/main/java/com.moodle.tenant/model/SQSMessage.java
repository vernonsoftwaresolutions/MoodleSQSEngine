package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class SQSMessage {
    /*
        ID of the requester that is creating the instance
     */
    private String id;
    /*
        Name of the ECS Stack that will be used to create the tenant
     */
    private String stackName;
    /*
        Name of the tenant that will be created.  This will end up being the stack name
        of the moodle container instance
     */
    private String clientName;
    /*
        Id of the VPC this will be deployed in
     */
    private String vpcId;
    /*
        DNS of the tenant
     */
    private String hostedZoneName;
    /*
        Load Balancer Target Rule Priority
     */
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SQSMessage{" +
                "id='" + id + '\'' +
                ", stackName='" + stackName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vpcId='" + vpcId + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
