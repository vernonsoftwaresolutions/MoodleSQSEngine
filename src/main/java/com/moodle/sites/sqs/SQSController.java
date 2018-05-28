package com.moodle.sites.sqs;

import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import com.moodle.sites.cloudformation.MoodleTemplate;
import com.moodle.sites.cloudformation.TemplateExecutor;
import com.moodle.sites.cloudformation.client.CloudformationClient;
import com.moodle.sites.exception.MoodleStackException;
import com.moodle.sites.model.SQSMessage;
import com.moodle.sites.model.StackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 9/6/17.
 */
@RestController
public class SQSController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String MOODLE_TENANT_NAME = "moodle_tenant.yml";
    private static String S3_BUCKET = "https://s3.amazonaws.com/moodle.templates/ecstenant/moodle_tenant.yml";

    private TemplateExecutor executor;
    private CloudformationClient client;

    public SQSController(TemplateExecutor executor, CloudformationClient client) {
        this.executor = executor;
        this.client = client;
    }

    @PostMapping("/worker/queue")
    public HttpStatus consumeMessage(@RequestBody SQSMessage message){
        logger.info("Received request " + message);

        Optional<List<Output>> outputs = null;
        try {
            logger.info("retrieving stack from request name {}", message.getStackName());

            //get the stack name of the ECS instance that will deploy the sites/client
            outputs = client.getStackOutput(new DescribeStacksRequest()
                    .withStackName(message.getStackName()));
            //if it doesn't exist, return an error because we can't generate the sites without it
            //since you know, that's the platform...
            if(!outputs.isPresent() || outputs.get().size() < 1){
                logger.error("No output returned, exiting request");
                return HttpStatus.NOT_FOUND;
            }

            logger.info("Retrieved output from stack {} value: {}", message.getStackName(), outputs.get().get(0));
            //otherwise the stack name that was passed in exists so
            //let's create the Template object that will be created
            MoodleTemplate moodleTenant = new MoodleTemplate(outputs, S3_BUCKET, message);

            logger.info("Created new moodletenant object {}", moodleTenant);

            //todo-actually use optional
            //now build the stack
            Stack stack = executor.buildStack(Optional.of(moodleTenant));

            StackResponse output = new StackResponse(stack.getStackId());

            logger.info("about to return output " + output);

        } catch (MoodleStackException | InterruptedException e) {

            logger.error("Error processing request ", e);

            return HttpStatus.INTERNAL_SERVER_ERROR;

        }
        //need to return okay so EB Worker will delete message?
        return HttpStatus.OK;
    }
}
