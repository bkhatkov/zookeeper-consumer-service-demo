package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateBasedRule;

public class VersionAwareRule extends PredicateBasedRule {

    private VersionAwarePredicate predicate;

    public VersionAwareRule(String version) {
        this.predicate = new VersionAwarePredicate(version);
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return this.predicate;
    }
}
