package com.moh.yehia.productservice.service.impl;

import com.moh.yehia.productservice.constant.RabbitMqProperties;
import com.moh.yehia.productservice.model.response.ProductCreatedEvent;
import com.moh.yehia.productservice.service.design.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;
    @Override
    public void sendToInventory(String productCode) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getTopicExchange(), rabbitMqProperties.getProductRoutingQueue(), new ProductCreatedEvent(productCode));
        log.info("new event of productCreation is sent to the inventory-service!");
    }
}
