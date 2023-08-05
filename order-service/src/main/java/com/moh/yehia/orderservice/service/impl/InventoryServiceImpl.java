package com.moh.yehia.orderservice.service.impl;

import com.moh.yehia.orderservice.model.request.OrderLineInquiry;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import com.moh.yehia.orderservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InventoryServiceImpl implements InventoryService {
    private final RestTemplate restTemplate;

    @Override
    public List<InventoryResponse> productInStock(List<OrderLineInquiry> orderLineInquiry) {
        ResponseEntity<List<InventoryResponse>> response = restTemplate.exchange(
                "lb://inventory-service/api/v1/inventory",
                HttpMethod.POST,
                new HttpEntity<>(orderLineInquiry),
                new ParameterizedTypeReference<>() {
                }
        );
        if (response.getStatusCode() != HttpStatus.OK) {
            return null;
        }
        log.info("response =>{}", response.getBody());
        return response.getBody();
    }
}
