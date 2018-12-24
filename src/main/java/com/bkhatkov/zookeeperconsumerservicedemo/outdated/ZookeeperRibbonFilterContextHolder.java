package com.bkhatkov.zookeeperconsumerservicedemo.outdated;

public class ZookeeperRibbonFilterContextHolder {

    private static final ThreadLocal<ZookeeperRibbonFilterContext> contextHolder = new InheritableThreadLocal<ZookeeperRibbonFilterContext>() {
        @Override
        protected ZookeeperRibbonFilterContext initialValue() {
            return new ZookeeperRibbonFilterContextImpl();
        }
    };

    public static ZookeeperRibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    public static void clearCurrentContext() {
        contextHolder.remove();
    }
}
