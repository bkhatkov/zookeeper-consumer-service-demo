package com.bkhatkov.zookeeperconsumerservicedemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultFeignConfig {

    @Bean
    public CustomServerListFilter serverListFilter() {
        CustomServerListFilter filter = new CustomServerListFilter();
        return filter;
    }

}
