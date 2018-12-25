package com.bkhatkov.zookeeperconsumerservicedemo;

import com.netflix.loadbalancer.AbstractServerListFilter;
import org.springframework.cloud.zookeeper.discovery.ZookeeperServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetadataAwareServerListFilter extends AbstractServerListFilter<ZookeeperServer> {

    private Map<String, String> attributes;

    public MetadataAwareServerListFilter(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<ZookeeperServer> getFilteredListOfServers(List<ZookeeperServer> list) {

        List<ZookeeperServer> result = new ArrayList<>();

        list.stream().forEach(zookeeperServer -> {
            Set<Map.Entry<String, String>> attrs = this.attributes.entrySet();
            Map<String, String> metadata = list.get(0).getInstance().getPayload().getMetadata();
            if (metadata.entrySet().containsAll(attrs)) {
                result.add(zookeeperServer);
            }
        });

        return result;
    }
}
