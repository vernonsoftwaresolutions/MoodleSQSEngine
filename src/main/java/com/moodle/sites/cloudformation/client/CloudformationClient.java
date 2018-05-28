package com.moodle.sites.cloudformation.client;

import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Output;
import com.moodle.sites.exception.MoodleStackException;

import java.util.List;
import java.util.Optional;

/**
 * Created by andrewlarsen on 8/29/17.
 */
public interface CloudformationClient {

    Optional<List<Output>> getStackOutput(DescribeStacksRequest request) throws MoodleStackException;
}
