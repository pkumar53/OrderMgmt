package com.ordermgmt.cart_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CartConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
