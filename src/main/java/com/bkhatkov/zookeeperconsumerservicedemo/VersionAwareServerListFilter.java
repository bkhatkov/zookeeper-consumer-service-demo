package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerListFilter;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import java.util.List;
import java.util.stream.Collectors;

public class VersionAwareServerListFilter extends AbstractServerListFilter<ZookeeperServer> {

    private String version;

    public VersionAwareServerListFilter(String version) {
        this.version = version;
    }

    @Override
    public List<ZookeeperServer> getFilteredListOfServers(List<ZookeeperServer> list) {
        return list.stream().
                filter(zookeeperServer -> (zookeeperServer.getInstance().getPayload().
                        getMetadata().get("version").compareToIgnoreCase(this.version) == 0))
                .collect(Collectors.toList());
    }
}
