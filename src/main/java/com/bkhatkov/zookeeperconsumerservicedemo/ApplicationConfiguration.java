package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyState;
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyWatcherListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class ApplicationConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    DependencyWatcherListener dependencyWatcherListener() {
        return new DependencyWatcherListener() {
            @Override
            public void stateChanged(String dependencyName, DependencyState newState) {
                System.out.println(dependencyName + " : " + newState.name());
            }
        };
    }

    @Bean public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }


//    @Bean
//    @ConditionalOnMissingBean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public DiscoveryEnabledRule metadataAwareRule() {
//        return new MetadataAwareRule();
//    }
}
