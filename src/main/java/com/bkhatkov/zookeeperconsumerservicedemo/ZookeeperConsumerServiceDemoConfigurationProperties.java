package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties
@RefreshScope
public class ZookeeperConsumerServiceDemoConfigurationProperties {

    @Value("${tenants:null}")
    private String tenantsConfigurations;

    @Value("${test_property_1:test_property_1}")
    private String test_property_1;
    private String test_property_2;
    private String test_property_3;

    @Value("${db.host:ERROR}")
    private String db_host;

    public ZookeeperConsumerServiceDemoConfigurationProperties() {
        System.out.println("SHIT2");
    }

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

    public String getDb_host() {
        return db_host;
    }

    public void setDb_host(String db_host) {
        System.out.println("SHIT");
        this.db_host = db_host;
    }

    public String getTenantsConfigurations() {
        return tenantsConfigurations;
    }

    public void setTenantsConfigurations(String tenantsConfigurations) {
        this.tenantsConfigurations = tenantsConfigurations;
    }
}
