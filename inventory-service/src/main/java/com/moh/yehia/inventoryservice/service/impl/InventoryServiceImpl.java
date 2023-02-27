package com.moh.yehia.inventoryservice.service.impl;

import com.moh.yehia.inventoryservice.model.entity.Inventory;
import com.moh.yehia.inventoryservice.model.response.InventoryResponse;
import com.moh.yehia.inventoryservice.repository.InventoryRepository;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isProductInStock(List<String> skuCodes) {
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);
        if (inventories == null || inventories.isEmpty()){
            return null;
        }
        return inventories.stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .productInStock(inventory.getQuantity() > 0)
                                .build())
                .collect(Collectors.toList());
    }
}
