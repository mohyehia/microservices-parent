package com.moh.yehia.orderservice.service.design;

import com.moh.yehia.orderservice.model.request.OrderLineInquiry;
import com.moh.yehia.orderservice.model.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> productInStock(List<OrderLineInquiry> orderLineInquiry);
}
