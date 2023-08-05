package com.moh.yehia.inventoryservice.listener;

import com.moh.yehia.inventoryservice.model.entity.Inventory;
import com.moh.yehia.inventoryservice.model.response.ProductCreatedEvent;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProductCreatedListener {
    private final InventoryService inventoryService;

    @RabbitListener(queues = "products_created_queue")
    public void handleProductCreation(ProductCreatedEvent productCreatedEvent) throws NoSuchAlgorithmException {
        log.info("Received notification for a new created product =>{}", productCreatedEvent);
        int quantity = SecureRandom.getInstanceStrong().nextInt(100);
        Inventory inventory = inventoryService.save(productCreatedEvent.getProductCode(), quantity);
        log.info("saved new inventory =>{}", inventory);
    }
}
