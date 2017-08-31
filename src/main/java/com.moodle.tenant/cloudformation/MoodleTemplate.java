package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Parameter;
import com.moodle.tenant.model.MoodleTenant;
import com.moodle.tenant.model.StackRequest;

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
    private String hostedZoneNameKey = "HostedZoneName";
    private String clientNameKey = "ClientName";

    public MoodleTemplate(Optional<List<Output>> outputs, String templateUrl, StackRequest request) {
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

}
