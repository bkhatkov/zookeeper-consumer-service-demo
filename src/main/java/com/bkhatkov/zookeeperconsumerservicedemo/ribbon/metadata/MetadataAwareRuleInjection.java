package com.bkhatkov.zookeeperconsumerservicedemo.ribbon.metadata;


import com.netflix.loadbalancer.ConfigurationBasedServerList;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConditionalOnClass(ConfigurationBasedServerList.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
public class MetadataAwareRuleInjection {
//    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public ZookeeperDiscoveryEnabledRule metadataAwareRule() {
//        return new MetadataAwareRule();
//    }
}
