package com.moh.yehia.inventoryservice.config;

import com.moh.yehia.inventoryservice.constant.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MqConfig {
    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public Queue productQueue() {
        return new Queue(rabbitMqProperties.getProductQueue());
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(rabbitMqProperties.getOrderQueue());
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitMqProperties.getTopicExchange());
    }

}