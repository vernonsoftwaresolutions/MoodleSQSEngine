package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyRequest;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.MoodleTenant;
import com.moodle.tenant.model.Output;
import org.apache.http.HttpStatus;

import java.util.logging.Logger;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class PostMoodleTenantHandler implements RequestHandler<MoodleTenant, ProxyResponse> {
    private final Logger log = Logger.getLogger(this.getClass().getName());
    private ProxyResponseFactory factory;

    public PostMoodleTenantHandler() {
        factory = new ProxyResponseFactory();

    }

    @Override
    public ProxyResponse handleRequest(MoodleTenant input, Context context) {
        log.info("Received request "+ input);
        Output output = new Output();
        output.setOutputValue("SOME VALUE");
        log.info("about to return output " + output);
        return factory.createResponse(output, HttpStatus.SC_OK, null);
    }
}
