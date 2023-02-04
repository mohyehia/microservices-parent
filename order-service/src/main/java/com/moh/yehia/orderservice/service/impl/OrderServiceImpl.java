package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.model.entity.Order;
import com.moh.yehia.orderservice.model.entity.OrderItem;
import com.moh.yehia.orderservice.model.request.OrderLineDTO;
import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.repository.OrderRepository;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void save(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequest.getOrderLines()
                .stream()
                .map(this::mapToOrderItemEntity).collect(Collectors.toList());
        order.setOrderItems(orderItems);
        orderRepository.save(order);
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
