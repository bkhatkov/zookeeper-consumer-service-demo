package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@RefreshScope
public class ZookeeperConsumerServiceDemoConfigurationProperties {

    @Value("${test_property_1:test_property_1}")
    private String test_property_1;
    private String test_property_2;
    private String test_property_3;

    public String getTest_property_1() {
        return test_property_1;
    }

    public void setTest_property_1(String test_property_1) {
        this.test_property_1 = test_property_1;
    }

    public String getTest_property_2() {
        return test_property_2;
    }

    public void setTest_property_2(String test_property_2) {
        this.test_property_2 = test_property_2;
    }

    public String getTest_property_3() {
        return test_property_3;
    }

    public void setTest_property_3(String test_property_3) {
        this.test_property_3 = test_property_3;
    }
}
