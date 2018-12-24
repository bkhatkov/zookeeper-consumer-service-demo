package com.bkhatkov.zookeeperconsumerservicedemo.ribbonoutdated;

import java.util.HashMap;
import java.util.Map;

public class ZookeeperRibbonFilterContextImpl implements ZookeeperRibbonFilterContext {

    private final Map<String, String> attributes = new HashMap<>();

    @Override
    public ZookeeperRibbonFilterContext add(String key, String value) {
        this.attributes.put(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return this.attributes.get(key);
    }

    @Override
    public ZookeeperRibbonFilterContext remove(String key) {
        this.attributes.remove(key);
        return this;
    }

    @Override
    public Map<String, String> getAttributes() {
        return this.attributes;
    }
}
