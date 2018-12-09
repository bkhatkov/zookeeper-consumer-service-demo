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

    @GetMapping("/getHelloWorld")
    public String getHelloWorld() {
        return zookeeperDependencyDemoClient.helloWorld();
    }

    @GetMapping("/discovery")
    public String hi() {
        StringBuilder result = new StringBuilder();
        result.append("Discovery: \r\n");
        result.append("Services: \r\n");
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            result.append(service + "\r\n");
        }
        result.append("Zookeeper Dependency Demo: \r\n");
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("zookeeper-dependency-demo");
        for (ServiceInstance serviceInstance: serviceInstances) {
            result.append(serviceInstance.getUri().toString() + "[" + serviceInstance.getServiceId() + "]" + "\r\n");
        }
        result.append("Properties:\r\n");
        result.append(zookeeperDemoConfigurationProperties.toString());
        return result.toString();
    }

    @GetMapping("/properties")
    public ZookeeperConsumerServiceDemoConfigurationProperties properties() {
        return zookeeperDemoConfigurationProperties;
    }
}
