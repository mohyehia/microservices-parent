package com.moh.yehia.inventoryservice.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.rabbitmq.config")
@Component
@Data
public class RabbitMqProperties {
    private String orderQueue;
    private String orderRoutingQueue;
    private String productQueue;
    private String productRoutingQueue;
    private String topicExchange;
}
