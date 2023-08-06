package com.moh.yehia.inventoryservice.listener;

import com.moh.yehia.inventoryservice.model.request.OrderRequest;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class OrderCreatedListener {
    private final InventoryService inventoryService;

    @RabbitListener(queues = "${spring.rabbitmq.config.orderQueue}")
    public void handleOrderCreation(OrderRequest orderRequest) {
        log.info("Received notification for a new created order =>{}", orderRequest);
        inventoryService.updateInventory(orderRequest.getOrderLines());
        log.info("updated the inventory for the above records");
    }
}
