package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class LoadBalancer {
    private String arn;
    private String dnsName;
    private String hostedZoneName;
    private String listener;

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getHostedZoneName() {
        return hostedZoneName;
    }

    public void setHostedZoneName(String hostedZoneName) {
        this.hostedZoneName = hostedZoneName;
    }

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    @Override
    public String toString() {
        return "LoadBalancer{" +
                "arn='" + arn + '\'' +
                ", dnsName='" + dnsName + '\'' +
                ", hostedZoneName='" + hostedZoneName + '\'' +
                ", listener='" + listener + '\'' +
                '}';
    }
}
