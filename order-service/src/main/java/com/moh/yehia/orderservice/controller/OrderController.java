package com.moh.yehia.orderservice.controller;

import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.OrderPlacedEvent;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;
import com.moh.yehia.orderservice.service.design.NotificationService;
import com.moh.yehia.orderservice.service.design.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Log4j2
public class OrderController {
    private final OrderService orderService;
    private final NotificationService notificationService;

    @PostMapping
    @CircuitBreaker(name = "inventory")
//    @Retry(name = "inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceOrderResponse placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        PlaceOrderResponse placeOrderResponse = orderService.save("mohammed", orderRequest);
        notificationService.sendToInventory(orderRequest);
        notificationService.sendToNotification(placeOrderResponse);
        return placeOrderResponse;
    }
}
