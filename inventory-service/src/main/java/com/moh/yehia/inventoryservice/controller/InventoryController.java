package com.moh.yehia.inventoryservice.controller;

import com.moh.yehia.inventoryservice.model.response.InventoryResponse;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> productInStock(@RequestParam("skuCode") List<String> skuCodes) {
        return new ResponseEntity<>(inventoryService.isProductInStock(skuCodes), HttpStatus.OK);
    }
}
