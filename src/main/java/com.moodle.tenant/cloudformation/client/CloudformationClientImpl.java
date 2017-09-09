package com.moodle.tenant.cloudformation.client;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;
import com.moodle.tenant.exception.MoodleStackException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 8/29/17.
 */
@Component
public class CloudformationClientImpl implements CloudformationClient{
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonCloudFormation cloudFormation;

    public CloudformationClientImpl(AmazonCloudFormation cloudFormation) {
        this.cloudFormation = cloudFormation;
    }

    @Override
    public Optional<List<Output>> getStackOutput(DescribeStacksRequest request) throws MoodleStackException {
        logger.info("Retrieving stack status with stackName " + request);

        DescribeStacksResult result = this.cloudFormation.describeStacks(request);
        logger.info("Returned result ", result);
        if(result.getStacks().isEmpty() || result.getStacks().size() > 1){
            logger.info("Result size is invalid");
            return Optional.empty();
        }
        return Optional.of(result.getStacks().get(0).getOutputs());
    }
}
