package com.moodle.tenant;

import com.amazonaws.services.cloudformation.model.Output;

import com.amazonaws.services.cloudformation.model.Parameter;
import com.moodle.tenant.cloudformation.MoodleTemplate;
import com.moodle.tenant.model.SQSMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by andrewlarsen on 8/27/17.
 */
public class MoodleTemplateTest {
    private String vpcKey = "VpcId";
    private String vpc = "vpc";
    private String priorityKey = "Priority";
    private String priority = "1";

    private String ecsClusterKey = "ecscluster";
    private String ecsCluster = "ecsclustervalue";

    private String ecslbarnKey = "ecslbarn";
    private String ecslbarn = "ecslbarnvalue";

    private String ecslbdnsnameKey = "ecslbdnsname";
    private String ecslbdnsname = "ecslbdnsnamevalue";

    private String ecslbhostedzoneidKey = "ecslbhostedzoneid";
    private String ecslbhostedzoneid = "ecslbhostedzoneidvalue";

    private String alblistenerKey = "alblistener";
    private String alblistener = "alblistenervalue";

    private String hostedZoneNameKey = "HostedZoneName";
    private String hostedZoneName = "HostedZoneNamevalue";

    private String clientNameKey = "ClientName";
    private String clientName = "ClientNamevalue";

    private String templatebody = "body";
    private String stackName = "stackname";
    private SQSMessage request;
    private List<Output> outputs;
    private MoodleTemplate template;
    @Before
    public void setup() {
        request = new SQSMessage();
        request.setStackName(stackName);
        request.setVpcId("vpc");
        request.setClientName(clientName);
        outputs = new ArrayList<Output>() {{
            add(new Output().withOutputKey(priorityKey).withOutputValue(priority));
            add(new Output().withOutputKey(ecsClusterKey).withOutputValue(ecsCluster));
            add(new Output().withOutputKey(ecslbarnKey).withOutputValue(ecslbarn));
            add(new Output().withOutputKey(ecslbdnsnameKey).withOutputValue(ecslbdnsname));
            add(new Output().withOutputKey(ecslbhostedzoneidKey).withOutputValue(ecslbhostedzoneid));
            add(new Output().withOutputKey(alblistenerKey).withOutputValue(alblistener));
            add(new Output().withOutputKey(hostedZoneNameKey).withOutputValue(hostedZoneName));

        }};



    }
    @Test
    public void createTemplateStackName() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(template.getStackName(), is(stackName));

    }
    @Test
    public void createTemplateCLientName() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), clientNameKey), is(clientName));

    }
    @Test
    public void createTemplatePriority() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), priorityKey), is(priority));

    }
    @Test
    public void createTemplateecsCluster() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), ecsClusterKey), is(ecsCluster));

    }
    @Test
    public void createTemplateecslbarn() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), ecslbarnKey), is(ecslbarn));

    }
    @Test
    public void createTemplateecslbdnsname() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), ecslbdnsnameKey), is(ecslbdnsname));

    }
    @Test
    public void createTemplateeecslbhostedzoneid() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), ecslbhostedzoneidKey), is(ecslbhostedzoneid));

    }
    @Test
    public void createTemplateeecslbalblistener() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), alblistenerKey), is(alblistener));

    }   @Test
    public void createTemplateeecslbhostedZoneName() throws Exception {
        template = new MoodleTemplate(Optional.of(outputs), templatebody, request);

        assertThat(getParameter(template.getParameters(), hostedZoneNameKey), is(hostedZoneName));

    }
    public String getParameter(List<Parameter> parameters, String key){
        return parameters.stream().filter(parameter ->
            parameter.getParameterKey().equals(key)
        ).findFirst().get().getParameterValue();
    }
}