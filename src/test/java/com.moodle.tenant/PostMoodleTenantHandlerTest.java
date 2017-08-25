package com.moodle.tenant;

import com.moodle.tenant.model.MoodleTenant;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class PostMoodleTenantHandlerTest {
    private PostMoodleTenantHandler handler = new PostMoodleTenantHandler();
    @Test
    public void handleRequest() throws Exception {

        assertThat(handler.handleRequest(new MoodleTenant(), null), is("Fin"));
    }

}