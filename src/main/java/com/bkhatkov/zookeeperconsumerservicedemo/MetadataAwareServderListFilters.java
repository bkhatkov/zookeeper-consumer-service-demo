package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerListFilter;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import java.util.List;
import java.util.stream.Collectors;

public class MetadataAwareServderListFilters {

    public static AbstractServerListFilter versionAwareServerListFilter(String version) {
        return new AbstractServerListFilter<ZookeeperServer>() {
            @Override
            public List<ZookeeperServer> getFilteredListOfServers(List<ZookeeperServer> list) {
                return list.stream().
                        filter(zookeeperServer -> (
                                (version != null && zookeeperServer.getInstance().getPayload().
                                        getMetadata().get("version").compareToIgnoreCase(version) == 0)) ||
                                (version == null && zookeeperServer.getInstance().getPayload().
                                        getMetadata().get("version").compareToIgnoreCase("V1") == 0))
                        .collect(Collectors.toList());
            }
        };
    }

}
