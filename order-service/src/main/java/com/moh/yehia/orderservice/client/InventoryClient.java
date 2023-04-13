package com.moh.yehia.orderservice.client;


import com.moh.yehia.orderservice.model.response.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/api/v1/inventory")
    List<InventoryResponse> productInStock(@RequestParam("skuCode") List<String> skuCodes);
}
