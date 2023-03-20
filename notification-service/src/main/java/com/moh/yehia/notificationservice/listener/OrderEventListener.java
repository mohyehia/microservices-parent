package com.moh.yehia.notificationservice.listener;

import com.moh.yehia.notificationservice.model.OrderPlacedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OrderEventListener {
    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        // send an email or sms to the customer with the order details
        log.info("Received new notification for order =>{}", orderPlacedEvent.getOrderNumber());
    }
}