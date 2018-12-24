package com.bkhatkov.zookeeperconsumerservicedemo.ribbonoutdated;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;

public class MetadataAwareRule extends PredicateBasedRule {

    private CompositePredicate predicate;

    public MetadataAwareRule() {

        this.predicate = CompositePredicate.withPredicates(new MetadataAwarePredicate(), new AvailabilityPredicate(this, null)).build();
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return this.predicate;
    }
}
