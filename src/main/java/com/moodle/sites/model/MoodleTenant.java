package com.moodle.sites.model;

/**
 * Created by andrewlarsen on 8/24/17.
 *
 *
 * VpcId=
 * Priority=
 * ecscluster=
 * ecslbarn=
 * ecslbdnsname=
 * ecslbhostedzoneid=
 * alblistener=
 * HostedZoneName=
 * ClientName=
 */
public class MoodleTenant {

    private String vpcId;
    private Integer priority;
    private String ecsCluser;
    private String hostedZoneName;
    private String clientName;
    private LoadBalancer loadBalancer;

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getEcsCluser() {
        return ecsCluser;
    }

    public void setEcsCluser(String ecsCluser) {
        this.ecsCluser = ecsCluser;
    }

    public String getHostedZoneName() {
        return hostedZoneName;
    }

    public void setHostedZoneName(String hostedZoneName) {
        this.hostedZoneName = hostedZoneName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    @Override
    public String toString() {
        return "MoodleTenant{" +
                "vpcId='" + vpcId + '\'' +
                ", priority=" + priority +
                ", ecsCluser='" + ecsCluser + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", loadBalancer=" + loadBalancer +
                '}';
    }
}
