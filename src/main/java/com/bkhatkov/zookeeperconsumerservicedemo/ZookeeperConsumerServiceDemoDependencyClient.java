package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class ZookeeperConsumerServiceDemoDependencyClient { //implements ApplicationContextAware {

    @Value("${backend-service-name}")
    private String backendServiceName;

    @Autowired
    private SpringClientFactory springClientFactory;

    @Autowired
    private BackendClient backendClient;

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST)
    public VersionAwareServerListFilter versionAwareServerListFilter() {
        return new VersionAwareServerListFilter(((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest().getHeader("version"));
    }

    @FeignClient(name = "${backend-service-name}") //zookeeper-backend-service-demo
    interface BackendClient {

        @RequestMapping(path = "/helloworld", method = RequestMethod.GET)
        @ResponseBody
        String helloWorld();
    }

    public String helloWorld() {

        Map<String, String> attrs = new HashMap<>();
        attrs.put("version",  ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest().getHeader("version"));
        if (((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest().getHeader("tenant") != null) {
            attrs.put("tenant", ((ServletRequestAttributes) RequestContextHolder.
                    currentRequestAttributes()).getRequest().getHeader("tenant"));
        }
        RequestContextHolder.currentRequestAttributes().setAttribute("filteringAttributes", attrs, 0);

        return backendClient.helloWorld();
    }

}
