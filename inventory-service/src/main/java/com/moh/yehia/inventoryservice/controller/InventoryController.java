package com.moh.yehia.inventoryservice.controller;

import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<Boolean> productInStock(@PathVariable("skuCode") String skuCode) {
        return new ResponseEntity<>(inventoryService.isProductInStock(skuCode), HttpStatus.OK);
    }
}
