package com.moodle.tenant.model;

/**
 * Created by andrewlarsen on 8/26/17.
 */
public class Output {
    private String outputValue;

    public String getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue = outputValue;
    }

    @Override
    public String toString() {
        return "Output{" +
                "outputValue='" + outputValue + '\'' +
                '}';
    }
}
