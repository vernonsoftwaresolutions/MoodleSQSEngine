package com.moodle.tenant.cloudformation.client;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;
import com.moodle.tenant.cloudformation.TemplateExecutor;
import com.moodle.tenant.exception.MoodleStackException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class CloudformationClientImpl implements CloudformationClient{
    final static Logger logger = Logger.getLogger(CloudformationClientImpl.class);

    private AmazonCloudFormation cloudFormation;

    public CloudformationClientImpl(AmazonCloudFormation cloudFormation) {
        this.cloudFormation = cloudFormation;
    }

    @Override
    public Optional<List<Output>> getStackOutput(DescribeStacksRequest request) throws MoodleStackException {
        logger.debug("Retrieving stack status with stackName " + request);

        DescribeStacksResult result = this.cloudFormation.describeStacks(request);
        if(result.getStacks().isEmpty() || result.getStacks().size() > 1){
            return Optional.empty();
        }
        return Optional.of(result.getStacks().get(0).getOutputs());
    }
}
