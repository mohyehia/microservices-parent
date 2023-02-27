package com.moh.yehia.inventoryservice.service.design;

import com.moh.yehia.inventoryservice.model.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isProductInStock(List<String> skuCodes);
}
