package com.moodle.sites.cloudformation.client;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public class CloudformationClientImplTest {
    @Mock
    private AmazonCloudFormation cloudFormation;
    private CloudformationClient client;
    private DescribeStacksRequest request;
    private DescribeStacksResult result;
    private List<Stack> stacks;
    private List<Output> outputs;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        client = new CloudformationClientImpl(cloudFormation);
        request = new DescribeStacksRequest();
        result = new DescribeStacksResult();
        stacks = new ArrayList<>();
    }
    @Test
    public void getStackOutput() throws Exception {
        given(cloudFormation.describeStacks(request)).willReturn(result);
        Optional<List<Output>> outputs = client.getStackOutput(request);
        assertThat(outputs.isPresent(), is(false));
    }
    @Test
    public void getStackOutputStacks() throws Exception {
        Stack stack = new Stack();
        stack.setOutputs(outputs);
        stacks.add(stack);
        result.setStacks(stacks);

        given(cloudFormation.describeStacks(request)).willReturn(result);
        Optional<List<Output>> outputs = client.getStackOutput(request);
        assertThat(outputs.isPresent(), is(true));
        assertThat(outputs.get(), is(stack.getOutputs()));
    }
}