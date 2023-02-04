package com.moh.yehia.orderservice.controller;

import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.service.design.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        orderService.save(orderRequest);
        return new ResponseEntity<>("Order saved successfully!", HttpStatus.CREATED);
    }
}
