package com.moh.yehia.productservice.config;

import com.moh.yehia.productservice.constant.RabbitMqProperties;
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
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitMqProperties.getTopicExchange());
    }

    @Bean
    public Binding binding(Queue productQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(topicExchange)
                .with(rabbitMqProperties.getProductRoutingQueue());
    }
}
