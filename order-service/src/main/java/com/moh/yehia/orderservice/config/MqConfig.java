package com.moh.yehia.orderservice.config;

import com.moh.yehia.orderservice.constant.RabbitMqProperties;
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
    public Queue orderQueue() {
        return new Queue(rabbitMqProperties.getOrderQueue());
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(rabbitMqProperties.getNotificationQueue());
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(topicExchange)
                .with(rabbitMqProperties.getOrderRoutingKey());
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(topicExchange)
                .with(rabbitMqProperties.getNotificationRoutingKey());
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitMqProperties.getTopicExchange());
    }
}
