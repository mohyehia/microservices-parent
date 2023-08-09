package com.moh.yehia.notificationservice.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.rabbitmq.config")
@Component
@Data
public class RabbitMqProperties {
    private String notificationQueue;
    private String topicExchange;
}