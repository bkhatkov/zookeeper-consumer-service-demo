package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerListFilter;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.ServerListFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class ZookeeperConsumerServiceDemoDependencyClient implements ApplicationContextAware {

    @Autowired
    private BackendClient backendClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancer;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @FeignClient(name = "zookeeper-backend-service-demo")
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
        final String requestedTenant = (attributes.getRequest().getHeader("tenant") == null ||
                attributes.getRequest().getHeader("tenant").compareToIgnoreCase("") ==0) ?
                "default" : attributes.getRequest().getHeader("tenant");

        SpringClientFactory springClientFactory = applicationContext.getBean(SpringClientFactory.class);
        ILoadBalancer loadBalancer = springClientFactory.getLoadBalancer("zookeeper-backend-service-demo");

        DynamicServerListLoadBalancer<ZookeeperServer> dynamicServerListLoadBalancer = (DynamicServerListLoadBalancer) loadBalancer;

        ServerListFilter<ZookeeperServer> filter = new AbstractServerListFilter<ZookeeperServer>() {
            @Override
            public List<ZookeeperServer> getFilteredListOfServers(List<ZookeeperServer> list) {
                List<ZookeeperServer> result = new ArrayList<>();
                for (ZookeeperServer zookeeperServer : list) {
                    if (zookeeperServer.getInstance().getPayload().getMetadata().get("version").compareToIgnoreCase(requestedVersion) == 0
                            && zookeeperServer.getInstance().getPayload().getMetadata().get("tenant").compareToIgnoreCase(requestedTenant) == 0) {
                        result.add(zookeeperServer);
                    }
                }
                return result;
                //list.stream().filter(zookeeperServer -> (zookeeperServer.getInstance().getPayload().getMetadata().get("version").compareToIgnoreCase(requestedVersion) == 0)).collect(Collectors.toList());
            }
        };

        dynamicServerListLoadBalancer.setFilter(filter);
        dynamicServerListLoadBalancer.updateListOfServers();

        return backendClient.helloWorld();
    }

}
