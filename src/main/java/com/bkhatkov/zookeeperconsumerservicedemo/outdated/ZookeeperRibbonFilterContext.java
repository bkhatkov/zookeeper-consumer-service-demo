package com.bkhatkov.zookeeperconsumerservicedemo.outdated;

import java.util.Map;

public interface ZookeeperRibbonFilterContext {
    ZookeeperRibbonFilterContext add(String key, String value);
    String get(String key);
    ZookeeperRibbonFilterContext remove(String key);
    Map<String, String> getAttributes();
}
