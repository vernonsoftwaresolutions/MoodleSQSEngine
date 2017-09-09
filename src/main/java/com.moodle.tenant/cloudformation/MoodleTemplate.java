package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.Output;
import com.moodle.tenant.model.SQSMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by andrewlarsen on 8/27/17.
 */
public class MoodleTemplate extends Template {

    //todo- create as properties
    private String vpcKey = "VpcId";
    private String priorityKey = "Priority";
    private String ecsClusterKey = "ecscluster";
    private String ecslbarnKey = "ecslbarn";
    private String ecslbdnsnameKey = "ecslbdnsname";
    private String ecslbhostedzoneidKey = "ecslbhostedzoneid";
    private String alblistenerKey = "alblistener";
    private String hostedZoneNameKey = "ecslbhostedzoneid";
    private String clientNameKey = "ClientName";

    public MoodleTemplate(Optional<List<Output>> outputs, String templateUrl, SQSMessage request) {
        parameters = new ArrayList<>();

        Map<String, String> moodleTenant = outputs.get().stream()
                .collect(Collectors.toMap(Output::getOutputKey, Output::getOutputValue));

        this.templateUrl = templateUrl;
        this.stackName = request.getStackName();
        this.parameters.add(createParameter(vpcKey, request.getVpcId()));
        this.parameters.add(createParameter(priorityKey, moodleTenant.get(priorityKey)));
        this.parameters.add(createParameter(ecsClusterKey, moodleTenant.get(ecsClusterKey)));
        this.parameters.add(createParameter(ecslbarnKey, moodleTenant.get(ecslbarnKey)));
        this.parameters.add(createParameter(ecslbdnsnameKey, moodleTenant.get(ecslbdnsnameKey)));
        this.parameters.add(createParameter(ecslbhostedzoneidKey, moodleTenant.get(ecslbhostedzoneidKey)));
        this.parameters.add(createParameter(alblistenerKey, moodleTenant.get(alblistenerKey)));
        this.parameters.add(createParameter(hostedZoneNameKey, moodleTenant.get(hostedZoneNameKey)));
        this.parameters.add(createParameter(clientNameKey, request.getClientName()));
    }

    @Override
    public String toString() {
        return "MoodleTemplate{" +
                "vpcKey='" + vpcKey + '\'' +
                ", priorityKey='" + priorityKey + '\'' +
                ", ecsClusterKey='" + ecsClusterKey + '\'' +
                ", ecslbarnKey='" + ecslbarnKey + '\'' +
                ", ecslbdnsnameKey='" + ecslbdnsnameKey + '\'' +
                ", ecslbhostedzoneidKey='" + ecslbhostedzoneidKey + '\'' +
                ", alblistenerKey='" + alblistenerKey + '\'' +
                ", hostedZoneNameKey='" + hostedZoneNameKey + '\'' +
                ", clientNameKey='" + clientNameKey + '\'' +
                '}';
    }
}
