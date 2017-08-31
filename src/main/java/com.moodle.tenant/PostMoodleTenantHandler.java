package com.moodle.tenant;

import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.moodle.tenant.cloudformation.MoodleTemplate;
import com.moodle.tenant.cloudformation.TemplateExecutor;
import com.moodle.tenant.cloudformation.client.CloudformationClient;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.StackRequest;
import com.moodle.tenant.model.StackResponse;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class PostMoodleTenantHandler implements RequestHandler<StackRequest, ProxyResponse> {
    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TemplateExecutor.class);
    private ProxyResponseFactory factory;
    private TemplateExecutor executor;
    private CloudformationClient client;
    private static String MOODLE_TENANT_NAME = "moodle_tenant.yml";
    private static String S3_BUCKET = "s3://moodle.templates/ecstenant/moodle_tenant.yml";
    public PostMoodleTenantHandler() {
        factory = new ProxyResponseFactory();
        executor = new TemplateExecutor(AmazonCloudFormationClientBuilder.defaultClient());
        
        //todo-refactor this to one call tp build client
        client = new CloudformationClientImpl(AmazonCloudFormationClientBuilder.defaultClient());
    }

    public PostMoodleTenantHandler(ProxyResponseFactory factory, TemplateExecutor executor,
                                   CloudformationClient client) {
        this.factory = factory;
        this.executor = executor;
        this.client = client;
    }

    @Override
    public ProxyResponse handleRequest(StackRequest request, Context context) {
        logger.info("Received request "+ request);
        try {

            Optional<List<Output>> outputs = client.getStackOutput(new DescribeStacksRequest()
                    .withStackName(request.getStackName()));

            if(!outputs.isPresent()){
                return factory.createErrorResponse(HttpStatus.SC_NOT_FOUND,
                        404, "Stack Not Found", null);
            }

            MoodleTemplate moodleTenant = new MoodleTemplate(outputs, S3_BUCKET, request );
            //todo-actually used optional
            Stack stack = executor.buildStack(Optional.of(moodleTenant));

            StackResponse output = new StackResponse(stack.getStackId());

            logger.info("about to return output " + output);
            return factory.createResponse(output, HttpStatus.SC_OK, null);

        } catch (Exception e) {
            logger.error("Error encountered processing request ", e);
            return factory.createErrorResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    500, "Internal Server Error", null);

        }

    }

}
