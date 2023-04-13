package com.moh.yehia.inventoryservice.service.impl;

import com.moh.yehia.inventoryservice.model.entity.Inventory;
import com.moh.yehia.inventoryservice.model.request.OrderLineInquiry;
import com.moh.yehia.inventoryservice.model.response.InventoryResponse;
import com.moh.yehia.inventoryservice.repository.InventoryRepository;
import com.moh.yehia.inventoryservice.service.design.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> isProductInStock(List<OrderLineInquiry> orderLineInquiries) {
        List<String> productCodes = orderLineInquiries.stream().map(OrderLineInquiry::getProductCode).collect(Collectors.toList());
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(productCodes);
        if (inventories == null || inventories.isEmpty()) {
            return null;
        }
        List<InventoryResponse> response = new ArrayList<>();
        for (OrderLineInquiry orderLineInquiry : orderLineInquiries) {
            inventories.forEach(inventory -> {
                if (inventory.getSkuCode().equals(orderLineInquiry.getProductCode())) {
                    response.add(new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() >= orderLineInquiry.getQuantity()));
                }
            });
        }

        return response;
    }
}
