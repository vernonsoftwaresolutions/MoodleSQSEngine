package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.*;
import com.moodle.tenant.exception.MoodleStackException;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Created by andrewlarsen on 8/27/17.
 */
@Component
public class TemplateExecutor {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private AmazonCloudFormation cloudFormation;

    public TemplateExecutor(AmazonCloudFormation cloudFormation){
        this.cloudFormation = cloudFormation;
    }

    /**
     * Method to create stack in using AWS Cloudformation SDK
     * @return

     */
    public Stack buildStack(Optional<Template> template) throws InterruptedException, MoodleStackException {
        if(!template.isPresent()){
            throw new IllegalArgumentException("No template provided");
        }

        logger.info("About to create stack with name {}",  template);
        CreateStackRequest stackRequest = new CreateStackRequest()
                .withCapabilities(Capability.CAPABILITY_IAM)
                .withStackName(template.get().getStackName())
                .withParameters(template.get().getParameters())
                .withTemplateURL(template.get().getTemplateUrl())
                .withTags(template.get().getTag());

        //todo-if exists update?
        CreateStackResult result = this.cloudFormation.createStack(stackRequest);

        DescribeStacksResult status;
        StackStatus stackStatus;
        do {
            status = describeStack(result.getStackId());
            //todo-assume stack is one and only one result
            stackStatus = StackStatus.valueOf(status.getStacks().get(0).getStackStatus());
            logger.info("Stack "+ template +" is in status " + stackStatus);
            Thread.sleep(4000);
        } while(stackStatus.equals(StackStatus.CREATE_IN_PROGRESS));

        if(!stackStatus.equals(StackStatus.CREATE_COMPLETE)){
            logger.info("Error creating stack");
            //todo-delete after failure
            throw new MoodleStackException(status.getStacks().get(0).getStackStatusReason());
        }
        return status.getStacks().get(0);
    }

    /**
     * Method to return stack details
     * @param stackId
     * @return
     */
    private DescribeStacksResult describeStack(String stackId){
        logger.debug("Retrieving stack status with stackId " + stackId);
        DescribeStacksRequest request = new DescribeStacksRequest();
        request.setStackName(stackId);
        return this.cloudFormation.describeStacks(request);
    }
}
