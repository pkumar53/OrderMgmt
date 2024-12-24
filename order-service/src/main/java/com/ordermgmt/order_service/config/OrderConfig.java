package com.ordermgmt.order_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}