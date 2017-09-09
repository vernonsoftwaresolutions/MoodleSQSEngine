package com.moodle.tenant.sqs;

import com.amazonaws.services.cloudformation.model.Stack;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodle.tenant.cloudformation.TemplateExecutor;
import com.moodle.tenant.cloudformation.client.CloudformationClient;
import com.moodle.tenant.exception.MoodleStackException;
import com.moodle.tenant.model.LoadBalancer;
import com.moodle.tenant.model.SQSMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andrewlarsen on 9/6/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SQSController.class)
public class SQSControllerTest {

    private SQSMessage tenant;
    private Stack stack;
    private HashMap map;
    private static String MOODLE_TENANT_NAME = "moodle_tenant.yml";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TemplateExecutor executor;
    @MockBean
    private CloudformationClient client;

    @Before
    public void setup(){
        tenant = new SQSMessage();
        tenant.setVpcId("VPCID");
        tenant.setClientName("CLIETNAME");
        tenant.setStackName("STACKNAME");
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.setArn("ARN");
        loadBalancer.setDnsName("DSNAME");
        loadBalancer.setHostedZoneName("HOZEDZONE");
        loadBalancer.setListener("LISTENER");
        stack = new Stack().withStackId("SOMEID");

        map = new HashMap<String, String>(){{
            put("HEADER", "VALUE");
        }};

    }
    @Test
    public void consumeMessage() throws Exception {
        given(client.getStackOutput(anyObject())).willReturn(Optional.of(new ArrayList<>()));

        given(executor.buildStack(anyObject())).willReturn(stack);
        this.mvc.perform(post("/worker/queue")
                .content(new ObjectMapper().writeValueAsString(tenant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void consumeMessageExecutor500() throws Exception {
        given(client.getStackOutput(anyObject())).willReturn(Optional.of(new ArrayList<>()));

        given(executor.buildStack(anyObject())).willThrow(new MoodleStackException("MOODLE EXCEPTION"));

        this.mvc.perform(post("/worker/queue")
                .content(new ObjectMapper().writeValueAsString(tenant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void consumeMessageClient500() throws Exception {
        given(client.getStackOutput(anyObject())).willThrow(new MoodleStackException("EXCEPTION"));

        this.mvc.perform(post("/worker/queue")
                .content(new ObjectMapper().writeValueAsString(tenant))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}