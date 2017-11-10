package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Tag;
import com.moodle.tenant.model.SQSMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by andrewlarsen on 8/27/17.
 * Class used to represent a moodle cloudfformation template
 * todo-update this to create a builder rather than building in the constructor
 */
public class MoodleTemplate extends Template {
    private static final String TAG_TYPE_KEY = "TYPE";
    private static final String TAG_TYPE_VALUE = "TENANT";
    private static final String TAG_ID_KEY = "ID";

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

    //todo-factor out SQSMessage, don't want to tightly couple request with Template
    public MoodleTemplate(Optional<List<Output>> outputs, String templateUrl, SQSMessage request) {
        List<Tag> tags = new ArrayList<>();
        //add tags
        tags.add(new Tag()
                .withKey(TAG_TYPE_KEY)
                .withValue(TAG_TYPE_VALUE));
        tags.add(new Tag()
                .withKey(TAG_ID_KEY)
                .withValue(request.getId()));
        //create tag
        this.tags = tags;
        parameters = new ArrayList<>();

        Map<String, String> moodleTenant = outputs.get().stream()
                .collect(Collectors.toMap(Output::getOutputKey, Output::getOutputValue));

        this.templateUrl = templateUrl;
        this.stackName = request.getClientName();
        this.parameters.add(createParameter(vpcKey, request.getVpcId()));
        this.parameters.add(createParameter(hostedZoneNameKey, request.getHostedZoneName()));
        this.parameters.add(createParameter(clientNameKey, request.getClientName()));
        this.parameters.add(createParameter(priorityKey, String.valueOf(request.getPriority())));
        this.parameters.add(createParameter(ecsClusterKey, moodleTenant.get(ecsClusterKey)));
        this.parameters.add(createParameter(ecslbarnKey, moodleTenant.get(ecslbarnKey)));
        this.parameters.add(createParameter(ecslbdnsnameKey, moodleTenant.get(ecslbdnsnameKey)));
        this.parameters.add(createParameter(ecslbhostedzoneidKey, moodleTenant.get(ecslbhostedzoneidKey)));
        this.parameters.add(createParameter(alblistenerKey, moodleTenant.get(alblistenerKey)));

    }

    @Override
    public String toString() {
        return "MoodleTemplate{" +
                "vpcKey='" + vpcKey + '\'' +
                "templateUrl='" + templateUrl + '\'' +
                "stackName='" + stackName + '\'' +
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
