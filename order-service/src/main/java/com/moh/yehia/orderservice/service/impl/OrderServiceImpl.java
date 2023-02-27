package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.model.entity.Order;
import com.moh.yehia.orderservice.model.entity.OrderItem;
import com.moh.yehia.orderservice.model.request.OrderLineDTO;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import com.moh.yehia.orderservice.repository.OrderRepository;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void save(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequest.getOrderLines()
                .stream()
                .map(this::mapToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        List<String> productCodes = orderItems.stream().map(OrderItem::getProductCode).collect(Collectors.toList());

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:9092/api/v1/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", productCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        if (inventoryResponses == null || inventoryResponses.length == 0){
            throw new IllegalArgumentException("Some products are not in stock, please try again later!");
        }
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isProductInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
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
