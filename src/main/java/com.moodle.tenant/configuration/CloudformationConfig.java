package com.moodle.tenant.configuration;

import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andrewlarsen on 9/6/17.
 */
@Configuration
public class CloudformationConfig {

    @Bean
    public AmazonCloudFormation amazonCloudFormation(){
        return AmazonCloudFormationClientBuilder.defaultClient();
    }
}
