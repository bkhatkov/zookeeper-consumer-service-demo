package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ZookeeperConsumerServiceDemoController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    ZookeeperConsumerServiceDemoDependencyClient zookeeperDependencyDemoClient;

    @Autowired
    ZookeeperConsumerServiceDemoConfigurationProperties zookeeperDemoConfigurationProperties;

    @GetMapping("/")
    public String demo() {
        return "Hello ZooKeeper!";
    }

    @GetMapping("/checkBackend")
    public String getHelloWorld() {
        //RibbonFilterContextHolder.getCurrentContext().add("version", "V2");
        return zookeeperDependencyDemoClient.helloWorld();
    }

    @GetMapping("/discovery/dependencies")
    public List<ServiceInstance> discoveryAll() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("zookeeper-backend-service-demo");
        return serviceInstances;
    }

    @GetMapping("/properties")
    public ZookeeperConsumerServiceDemoConfigurationProperties properties() {
        return zookeeperDemoConfigurationProperties;
    }
}
