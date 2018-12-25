package com.bkhatkov.zookeeperconsumerservicedemo.ribbon.metadata;

import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MetadataAwarePredicate extends ZookeeperDiscoveryEnabledPredicate {

    @Override
    protected boolean apply(ZookeeperServer server) {
        System.out.println("PREDIC " + Thread.currentThread().getName());
        //ZookeeperRibbonFilterContext context = ZookeeperRibbonFilterContextHolder.getCurrentContext();

        RequestAttributes context = RequestContextHolder.currentRequestAttributes();

        //System.out.println("PREDIC n1 " + RequestContextHolder.currentRequestAttributes().getAttribute("filteringAttributes", 0));
        //System.out.println("PREDIC " + context.get("version"));
        System.out.println("PREDIC " + ((Map<String, String>) context.getAttribute("filteringAttributes", 0)).toString());
        Set<Map.Entry<String, String>> attributes
                = Collections.unmodifiableSet(((Map<String, String>) context.getAttribute("filteringAttributes", 0)).entrySet());
        Map<String, String> metadata = server.getInstance().getPayload().getMetadata();
        return metadata.entrySet().containsAll(attributes);

        //return ((ZookeeperServer) predicateKey.getServer()).getInstance().getPayload().getMetadata().get("version").compareToIgnoreCase(this.version) == 0;
    }
}
