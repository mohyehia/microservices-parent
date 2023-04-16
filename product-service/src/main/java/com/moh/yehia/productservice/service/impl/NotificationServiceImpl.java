package com.moh.yehia.productservice.service.impl;

import com.moh.yehia.productservice.model.response.ProductCreatedEvent;
import com.moh.yehia.productservice.service.design.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaTopic;

    @Override
    public void sendToInventory(String productCode) {
        kafkaTemplate.send(kafkaTopic, new ProductCreatedEvent(productCode));
        log.info("new notification is sent to the inventory-service!");
    }
}
