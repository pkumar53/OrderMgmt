package com.ordermgmt.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    public Queue orderStatusQueue() {
        return new Queue("order_status_queue", true);
    }
}
