package com.moh.yehia.orderservice.controller;

import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;
import com.moh.yehia.orderservice.service.design.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory")
    @TimeLimiter(name = "inventory", fallbackMethod = "fallback")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<PlaceOrderResponse>> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(orderService.save(orderRequest), HttpStatus.CREATED));
    }

    public CompletableFuture<ResponseEntity<PlaceOrderResponse>> fallback(Exception e) {
        e.printStackTrace();
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(new PlaceOrderResponse("Failed", "Oops! Something went wrong, please try again after some time!", null), HttpStatus.OK));
    }
}
