package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.model.entity.Order;
import com.moh.yehia.orderservice.model.entity.OrderItem;
import com.moh.yehia.orderservice.model.request.OrderLineDTO;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import com.moh.yehia.orderservice.model.response.OrderPlacedEvent;
import com.moh.yehia.orderservice.repository.OrderRepository;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String kafkaTopic;

    @Override
    public String save(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequest.getOrderLines()
                .stream()
                .map(this::mapToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        List<String> productCodes = orderItems.stream().map(OrderItem::getProductCode).collect(Collectors.toList());

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpan(inventoryServiceLookup.start())) {
            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("lb://inventory-service/api/v1/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", productCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
            if (inventoryResponses == null || inventoryResponses.length == 0) {
                throw new IllegalArgumentException("Some products are not in stock, please try again later!");
            }
            boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isProductInStock);
            if (allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send(kafkaTopic, new OrderPlacedEvent(order.getOrderNumber()));
                return "Order saved successfully!";
            } else {
                throw new IllegalArgumentException("Some products are not in stock, please try again later!");
            }
        } finally {
            inventoryServiceLookup.end();
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
