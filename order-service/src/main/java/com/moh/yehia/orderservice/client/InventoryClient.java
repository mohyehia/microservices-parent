package com.moh.yehia.orderservice.client;


import com.moh.yehia.orderservice.client.fallback.InventoryClientFallback;
import com.moh.yehia.orderservice.model.request.OrderLineInquiry;
import com.moh.yehia.orderservice.model.response.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-service", fallback = InventoryClientFallback.class)
public interface InventoryClient {
    @PostMapping("/api/v1/inventory")
    List<InventoryResponse> productInStock(@RequestBody List<OrderLineInquiry> orderLineInquiry);
}
