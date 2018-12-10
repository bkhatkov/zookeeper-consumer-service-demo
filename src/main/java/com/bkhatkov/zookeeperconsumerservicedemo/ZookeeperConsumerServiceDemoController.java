package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return zookeeperDependencyDemoClient.helloWorld();
    }

    @GetMapping("/discovery")
    public Map<String, List<Object>> discovery() {
        Map<String, List<Object>> res = new HashMap<>();
        res.put("services", new ArrayList<>());
        res.put("dependencies", new ArrayList<>());
        res.put("configuration", new ArrayList<>());

        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<Object> temp = res.get("services");
            temp.add(service);
            res.put("services", temp);
        }
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("zookeeper-backend-service-demo");
        List<Object> temp = res.get("dependencies");
        temp.add(serviceInstances);
        res.put("dependencies", temp);

        List<Object> temp2 = res.get("configuration");
        Map<String, String> props = new HashMap<>();
        props.put("test_propery_1", zookeeperDemoConfigurationProperties.getTest_property_1());
        temp2.add(props);
        res.put("configuration", temp2);
        return res;
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
