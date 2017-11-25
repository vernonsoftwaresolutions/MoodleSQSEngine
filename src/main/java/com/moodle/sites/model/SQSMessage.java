package com.moodle.sites.model;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class SQSMessage {

    /*
        Id of the account that is creating the site
     */
    private String accountId;
    /*
        Id of the site that is to be generated
     */
    private String siteId;
    /*
        Name of the ECS Stack that will be used to create the sites
     */
    private String stackName;
    /*
        Name of the sites that will be created.  This will end up being the stack name
        of the moodle container instance
     */
    private String clientName;
    /*
        Id of the VPC this will be deployed in
     */
    private String vpcId;
    /*
        DNS of the sites
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


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Override
    public String toString() {
        return "SQSMessage{" +
                "accountId='" + accountId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", stackName='" + stackName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", vpcId='" + vpcId + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", priority=" + priority +
                '}';
    }
}
