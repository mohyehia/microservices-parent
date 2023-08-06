package com.moh.yehia.inventoryservice.service.design;

import com.moh.yehia.inventoryservice.model.entity.Inventory;
import com.moh.yehia.inventoryservice.model.request.OrderLineDTO;
import com.moh.yehia.inventoryservice.model.request.OrderLineInquiry;
import com.moh.yehia.inventoryservice.model.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isProductInStock(List<OrderLineInquiry> orderLineInquiries);

    Inventory save(String productCode, int quantity);

    void updateInventory(List<OrderLineDTO> orderLines);
}
