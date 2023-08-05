package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.exception.InvalidOrderException;
import com.moh.yehia.orderservice.model.entity.Order;
import com.moh.yehia.orderservice.model.entity.OrderItem;
import com.moh.yehia.orderservice.model.request.OrderLineDTO;
import com.moh.yehia.orderservice.model.request.OrderLineInquiry;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;
import com.moh.yehia.orderservice.repository.OrderRepository;
import com.moh.yehia.orderservice.service.design.InventoryService;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    //    private final InventoryClient inventoryClient;
    private final InventoryService inventoryService;

    @Override
    public PlaceOrderResponse save(String username, OrderRequest orderRequest) {
        log.info("start creating new order");
        Order order = new Order();
        order.setUsername(username);
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequest.getOrderLines()
                .stream()
                .map(this::mapToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        List<OrderLineInquiry> orderLineInquiries = new ArrayList<>();
        orderItems.forEach(orderItem -> orderLineInquiries.add(new OrderLineInquiry(orderItem.getProductCode(), orderItem.getQuantity())));
        List<InventoryResponse> inventoryResponses = inventoryService.productInStock(orderLineInquiries);

        log.info("inventoryResponse =>{}", inventoryResponses);
        if (inventoryResponses == null || inventoryResponses.isEmpty()) {
            throw new InvalidOrderException("Some products are not in stock, please try again later!");
        }
        boolean allProductsInStock = inventoryResponses.stream().allMatch(InventoryResponse::isProductInStock);
        if (allProductsInStock) {
            order = orderRepository.save(order);
            log.info("Order saved successfully with number =>{}", order.getOrderNumber());
//            kafkaTemplate.send(kafkaTopic, new OrderPlacedEvent(order.getOrderNumber(), username));
            return new PlaceOrderResponse("SUCCESS", "Order saved successfully!", order.getOrderNumber());
        } else {
            throw new InvalidOrderException("Some products are not in stock, please try again later!");
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
