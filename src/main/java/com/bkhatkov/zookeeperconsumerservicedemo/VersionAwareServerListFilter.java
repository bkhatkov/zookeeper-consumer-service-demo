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

        Double minVersion = list.stream().mapToDouble(value -> Double.valueOf(value.getInstance().getPayload().getMetadata().get("versionn"))).min().getAsDouble();
//        System.out.println(String.valueOf(minVersion));

        return list.stream().
                filter(zookeeperServer -> (
                        (this.version != null && zookeeperServer.getInstance().getPayload().
                                getMetadata().get("version").compareToIgnoreCase(this.version) == 0)) ||
                        ((this.version == null || this.version.compareToIgnoreCase("") == 0)
                                && zookeeperServer.getInstance().getPayload().
                                getMetadata().get("version").compareToIgnoreCase(String.valueOf(minVersion)) == 0))
                .collect(Collectors.toList());
    }
}
