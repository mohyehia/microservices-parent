package com.moh.yehia.inventoryservice.config;

import com.moh.yehia.inventoryservice.constant.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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
    public Binding productBinding(Queue productQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(topicExchange)
                .with(rabbitMqProperties.getProductRoutingQueue());
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(rabbitMqProperties.getOrderQueue());
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(topicExchange)
                .with(rabbitMqProperties.getOrderRoutingQueue());
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitMqProperties.getTopicExchange());
    }
}
