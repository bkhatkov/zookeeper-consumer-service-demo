package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

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

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST)
    public MetadataAwareServderListFilter metadataAwareServderListFilter() {
        Map<String, String> filters = new HashMap<>();
        filters.put("version", ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest().getHeader("version"));
        filters.put("tenant", ((ServletRequestAttributes) RequestContextHolder.
                currentRequestAttributes()).getRequest().getHeader("tenant"));
        return new MetadataAwareServderListFilter(filters);
    }

    @FeignClient(name = "${backend-service-name}") //zookeeper-backend-service-demo
    interface BackendClient {

        @RequestMapping(path = "/helloworld", method = RequestMethod.GET)
        @ResponseBody
        String helloWorld();
    }

    public String helloWorld() {
        DynamicServerListLoadBalancer<ZookeeperServer> dynamicServerListLoadBalancer =
                (DynamicServerListLoadBalancer) this.springClientFactory.getLoadBalancer(backendServiceName);

        dynamicServerListLoadBalancer.setFilter(metadataAwareServderListFilter());
        dynamicServerListLoadBalancer.updateListOfServers();

        return backendClient.helloWorld();
    }

}
