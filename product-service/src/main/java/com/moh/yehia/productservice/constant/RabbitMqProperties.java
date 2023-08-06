package com.moh.yehia.productservice.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.rabbitmq.config")
@Component
@Data
public class RabbitMqProperties {
    private String productQueue;
    private String topicExchange;
    private String productRoutingQueue;
}
