package com.bkhatkov.zookeeperconsumerservicedemo;

import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class ZookeeperConsumerServiceDemoDependencyClient {

    @Autowired
    private BackendClient backendClient;

    @FeignClient(name = "zookeeper-backend-service-demo")
    interface BackendClient {

        @RequestMapping(path = "/helloworld", method = RequestMethod.GET)
        @ResponseBody
        String helloWorld();
    }

    public String helloWorld() {
        return backendClient.helloWorld();
    }

}
