package com.moh.yehia.inventoryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Bean
    public Queue productQueue() {
        return new Queue("products_created_queue");
    }

    @Bean
    public Queue orderQueue(){
        return new Queue("order_created_queue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("microservices_exchange");
    }

    @Bean
    public Binding productBinding(Queue productQueue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(topicExchange)
                .with("product_routing_key");
    }

    @Bean
    public Binding orderBinding(Queue orderQueue, TopicExchange topicExchange){
        return BindingBuilder
                .bind(orderQueue)
                .to(topicExchange)
                .with("order_created_routing_key");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
