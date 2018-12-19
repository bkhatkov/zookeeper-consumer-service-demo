package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.client.IClientConfigAware;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerListFilter;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


public class CustomServerListFilter extends AbstractServerListFilter implements ServerListFilter {
    @Autowired
    private DiscoveryClient discoveryClient;



//     @Autowired
//     private HttpServletRequest request;

    @Override
    public List getFilteredListOfServers(List list) {
        List<Server> result = new ArrayList<>();

//        HttpServletRequest curRequest =
//                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//                        .getRequest();
        for (Object server : list) {
            System.out.println("WOW: " + server.toString() + " - " +
                    ((ZookeeperServer) server).getMetaInfo().getAppName() + " - " +
                    ((ZookeeperServer) server).getMetaInfo().getServiceIdForDiscovery() + " - " //+
//                    curRequest.getMethod()
            );

        }

        return list;
    }
}
