package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.client.InventoryClient;
import com.moh.yehia.orderservice.exception.InvalidOrderException;
import com.moh.yehia.orderservice.model.entity.Order;
import com.moh.yehia.orderservice.model.entity.OrderItem;
import com.moh.yehia.orderservice.model.request.OrderLineDTO;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import com.moh.yehia.orderservice.model.response.OrderPlacedEvent;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;
import com.moh.yehia.orderservice.repository.OrderRepository;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaTopic;

    @Override
    public PlaceOrderResponse save(OrderRequest orderRequest) {
        log.info("start creating new order");
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequest.getOrderLines()
                .stream()
                .map(this::mapToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        List<String> productCodes = orderItems.stream().map(OrderItem::getProductCode).collect(Collectors.toList());

        List<InventoryResponse> inventoryResponses = inventoryClient.productInStock(productCodes);
        log.info("inventoryResponse =>{}", inventoryResponses);
        if (inventoryResponses == null || inventoryResponses.isEmpty()) {
            throw new InvalidOrderException("Some products are not in stock, please try again later!");
        }
        boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isProductInStock);
        if (allProductsInStock) {
            order = orderRepository.save(order);
            log.info("Order saved successfully with number =>{}", order.getOrderNumber());
//            kafkaTemplate.send(kafkaTopic, new OrderPlacedEvent(order.getOrderNumber()));
            return new PlaceOrderResponse("SUCCESS", "Order saved successfully!", order.getOrderNumber());
        } else {
            throw new IllegalArgumentException("Some products are not in stock, please try again later!");
        }
    }

    private OrderItem mapToOrderItemEntity(OrderLineDTO orderLineDTO) {
        return OrderItem
                .builder()
                .productCode(orderLineDTO.getProductCode())
                .price(orderLineDTO.getPrice())
                .quantity(orderLineDTO.getQuantity())
                .build();
    }
}
