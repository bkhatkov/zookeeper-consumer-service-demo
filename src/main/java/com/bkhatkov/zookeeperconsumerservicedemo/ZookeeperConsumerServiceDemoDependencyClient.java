package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Autowired
//    private LoadBalancerClient loadBalancer;
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

    @FeignClient(name = "${backend-service-name}") //zookeeper-backend-service-demo
    interface BackendClient {

        @RequestMapping(path = "/helloworld", method = RequestMethod.GET)
        @ResponseBody
        String helloWorld();
    }

    public String helloWorld() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final String requestedVersion = (attributes.getRequest().getHeader("version") == null ||
                attributes.getRequest().getHeader("version").compareToIgnoreCase("") ==0) ?
                    "V1" : attributes.getRequest().getHeader("version");
//        final String requestedTenant = (attributes.getRequest().getHeader("tenant") == null ||
//                attributes.getRequest().getHeader("tenant").compareToIgnoreCase("") ==0) ?
//                "default" : attributes.getRequest().getHeader("tenant");

        DynamicServerListLoadBalancer<ZookeeperServer> dynamicServerListLoadBalancer =
                (DynamicServerListLoadBalancer) this.springClientFactory.getLoadBalancer(backendServiceName);

//        dynamicServerListLoadBalancer.setFilter(new VersionAwareServerListFilter(requestedVersion));

//        dynamicServerListLoadBalancer.setFilter(MetadataAwareServderListFilters.versionAwareServerListFilter(requestedVersion));
        dynamicServerListLoadBalancer.setFilter(versionAwareServerListFilter());
        dynamicServerListLoadBalancer.updateListOfServers();

//        ZookeeperRibbonFilterContext context = ZookeeperRibbonFilterContextHolder.getCurrentContext();
//        context.add("version", "V100");

        return backendClient.helloWorld();
    }

}
