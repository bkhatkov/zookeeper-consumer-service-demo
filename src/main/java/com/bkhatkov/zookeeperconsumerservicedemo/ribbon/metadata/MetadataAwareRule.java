package com.bkhatkov.zookeeperconsumerservicedemo.ribbon.metadata;

import com.netflix.loadbalancer.CompositePredicate;

public class MetadataAwareRule extends ZookeeperDiscoveryEnabledRule {

    private CompositePredicate predicate;

    public MetadataAwareRule() {
        //this.predicate = CompositePredicate.withPredicates(new MetadataAwarePredicate(), new AvailabilityPredicate(this, null)).build();
        this(new MetadataAwarePredicate());


    }

    public MetadataAwareRule(ZookeeperDiscoveryEnabledPredicate predicate) {
        super(predicate);
    }
}
