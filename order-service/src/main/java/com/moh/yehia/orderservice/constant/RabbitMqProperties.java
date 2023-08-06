package com.moh.yehia.orderservice.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.rabbitmq.config")
@Component
@Data
public class RabbitMqProperties {
    private String orderQueue;
    private String orderRoutingKey;
    private String notificationQueue;
    private String notificationRoutingKey;
    private String topicExchange;

}
