package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.constant.RabbitMqProperties;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.OrderPlacedEvent;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;
import com.moh.yehia.orderservice.service.design.NotificationService;
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
    public void sendToInventory(OrderRequest orderRequest) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getTopicExchange(), rabbitMqProperties.getOrderRoutingKey(), orderRequest);
        log.info("order is being sent successfully to inventory-service!");
    }

    @Override
    public void sendToNotification(OrderPlacedEvent orderPlacedEvent) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getTopicExchange(), rabbitMqProperties.getNotificationRoutingKey(), orderPlacedEvent);
        log.info("notification is being sent successfully to notification-service!");
    }
}
