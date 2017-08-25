package com.moodle.tenant;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.moodle.tenant.model.MoodleTenant;

import java.util.logging.Logger;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class PostMoodleTenantHandler implements RequestHandler<MoodleTenant, String> {
    private final Logger log = Logger.getLogger(this.getClass().getName());


    @Override
    public String handleRequest(MoodleTenant input, Context context) {
        log.info("Received request "+ input);

        return "Fin";
    }
}
