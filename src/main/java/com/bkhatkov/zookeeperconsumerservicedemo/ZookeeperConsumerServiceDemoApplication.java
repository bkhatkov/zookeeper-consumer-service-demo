package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Configuration
@EnableDiscoveryClient
@EnableHystrixDashboard
public class ZookeeperConsumerServiceDemoApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperConsumerServiceDemoApplication.class, args);
    }



}
