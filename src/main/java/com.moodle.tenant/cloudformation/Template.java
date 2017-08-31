package com.moodle.tenant.cloudformation;

import com.amazonaws.services.cloudformation.model.Parameter;

import java.util.List;

/**
 * Created by andrewlarsen on 8/27/17.
 */
public abstract class Template {
    protected String stackName;
    protected String templateUrl;
    protected List<Parameter> parameters;

    public String getStackName() {
        return stackName;
    }

    public void setStackName(String stackName) {
        this.stackName = stackName;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateBody) {
        this.templateUrl = templateBody;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Parameter createParameter(String key, String value){
        return new Parameter().withParameterKey(key).withParameterValue(value);
    }

    @Override
    public String toString() {
        return "Template{" +
                "stackName='" + stackName + '\'' +
                ", templateBody='" + templateUrl + '\'' +
                ", parameters=" + parameters +
                '}';
    }
}
