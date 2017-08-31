package com.moodle.tenant;

import com.amazonaws.services.cloudformation.model.Stack;
import com.moodle.tenant.cloudformation.TemplateExecutor;
import com.moodle.tenant.cloudformation.client.CloudformationClient;
import com.moodle.tenant.exception.MoodleStackException;
import com.moodle.tenant.factory.ProxyResponseFactory;
import com.moodle.tenant.lambda.ProxyResponse;
import com.moodle.tenant.model.LoadBalancer;
import com.moodle.tenant.model.StackRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Created by andrewlarsen on 8/24/17.
 */
public class PostMoodleTenantHandlerTest {
    private PostMoodleTenantHandler handler;
    private StackRequest tenant;
    private Stack stack;
    private ProxyResponse response;
    private HashMap map;
    private ProxyResponse errorResponse;
    private static String MOODLE_TENANT_NAME = "moodle_tenant.yml";

    @Mock
    TemplateExecutor executor;
    @Mock
    ProxyResponseFactory factory;
    @Mock
    CloudformationClient client;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        tenant = new StackRequest();
        tenant.setVpcId("VPCID");
        tenant.setClientName("CLIETNAME");
        tenant.setStackName("STACKNAME");
        LoadBalancer loadBalancer = new LoadBalancer();
        loadBalancer.setArn("ARN");
        loadBalancer.setDnsName("DSNAME");
        loadBalancer.setHostedZoneName("HOZEDZONE");
        loadBalancer.setListener("LISTENER");
        stack = new Stack().withStackId("SOMEID");
        handler = new PostMoodleTenantHandler(factory, executor, client);
        response = new ProxyResponse();
        response.setBody("HELLO");
        response.setStatusCode(200);
        map = new HashMap<String, String>(){{
            put("HEADER", "VALUE");
        }};
        response.setHeaders(map);
        errorResponse = new ProxyResponse();
        errorResponse.setHeaders(map);
        errorResponse.setStatusCode(500);
        errorResponse.setBody("ERROR");
    }
    @Test
    public void handleRequest() throws Exception {
        given(client.getStackOutput(anyObject())).willReturn(Optional.of(new ArrayList<>()));

        given(executor.buildStack(anyObject())).willReturn(stack);
        given(factory.createResponse(anyObject(),eq(200), anyObject())).willReturn(response);
        assertThat(handler.handleRequest(tenant, null).getBody(), is(response.getBody()));
    }
    @Test
    public void handleRequestStatusCodeIsReturned() throws Exception {
        given(client.getStackOutput(anyObject())).willReturn(Optional.of(new ArrayList<>()));

        given(executor.buildStack(anyObject())).willReturn(stack);
        given(factory.createResponse(anyObject(),eq(200), anyObject())).willReturn(response);
        assertThat(handler.handleRequest(tenant, null).getStatusCode(), is(response.getStatusCode()));
    }
    @Test
    public void handleRequestHeaderIsReturned() throws Exception {
        given(client.getStackOutput(anyObject())).willReturn(Optional.of(new ArrayList<>()));

        given(executor.buildStack(anyObject())).willReturn(stack);
        given(factory.createResponse(anyObject(),eq(200), anyObject())).willReturn(response);
        assertThat(handler.handleRequest(tenant, null).getHeaders().get("HEADER"), is(response.getHeaders().get("HEADER")));
    }
    @Test
    public void handleRequestExceptionIs500() throws Exception {

        given(executor.buildStack(anyObject())).willThrow(new MoodleStackException("MOODLE EXCEPTION"));
        given(factory.createErrorResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,
                500, "Internal Server Error", null)).willReturn(errorResponse);
        assertThat(handler.handleRequest(tenant, null).getStatusCode(), is(errorResponse.getStatusCode()));
    }
    @Test
    public void handleRequestExceptionIsHeaders() throws Exception {

        given(executor.buildStack(anyObject())).willThrow(new MoodleStackException("MOODLE EXCEPTION"));
        given(factory.createErrorResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,
                500, "Internal Server Error", null)).willReturn(errorResponse);
        assertThat(handler.handleRequest(tenant, null).getHeaders(), is(errorResponse.getHeaders()));
    }

}