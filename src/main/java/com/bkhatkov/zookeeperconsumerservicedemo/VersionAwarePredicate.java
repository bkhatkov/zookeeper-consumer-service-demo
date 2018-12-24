package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import javax.annotation.Nullable;

public class VersionAwarePredicate extends AbstractServerPredicate {

    private String version;

    public VersionAwarePredicate(String version) {
        this.version = version;
    }

    @Override
    public boolean apply(@Nullable PredicateKey predicateKey) {
        return ((ZookeeperServer) predicateKey.getServer()).getInstance().getPayload().getMetadata().get("version").compareToIgnoreCase(this.version) == 0;
    }
}
