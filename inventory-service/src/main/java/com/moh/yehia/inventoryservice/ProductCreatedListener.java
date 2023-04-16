package com.moh.yehia.inventoryservice;

import com.moh.yehia.inventoryservice.model.entity.Inventory;
import com.moh.yehia.inventoryservice.model.response.ProductCreatedEvent;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProductCreatedListener {
    private final InventoryService inventoryService;

    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void handleProductCreation(ProductCreatedEvent productCreatedEvent) {
        log.info("Received notification for a new created product =>{}", productCreatedEvent);
        int quantity = new Random().nextInt(100);
        Inventory inventory = inventoryService.save(productCreatedEvent.getProductCode(), quantity);
        log.info("saved new inventory =>{}", inventory);
    }
}
