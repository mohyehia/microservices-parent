package com.moh.yehia.orderservice.service.design;

import com.moh.yehia.orderservice.model.request.OrderRequest;
import com.moh.yehia.orderservice.model.response.PlaceOrderResponse;

public interface OrderService {
    PlaceOrderResponse save(OrderRequest orderRequest);
}
