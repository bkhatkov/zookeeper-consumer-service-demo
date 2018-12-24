package com.bkhatkov.zookeeperconsumerservicedemo.ribbonoutdated;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class MetadataAwarePredicate extends AbstractServerPredicate {

    public MetadataAwarePredicate() {

    }

    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {

        ZookeeperRibbonFilterContext context = ZookeeperRibbonFilterContextHolder.getCurrentContext();
        Set<Map.Entry<String, String>> attributes = context.getAttributes().entrySet();
        Map<String, String> metadata = ((ZookeeperServer) predicateKey.getServer()).getInstance().getPayload().getMetadata();
        return metadata.entrySet().containsAll(attributes);

        //return ((ZookeeperServer) predicateKey.getServer()).getInstance().getPayload().getMetadata().get("version").compareToIgnoreCase(this.version) == 0;
    }
}
