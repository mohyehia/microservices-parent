package com.moh.yehia.orderservice.service.design;

import com.moh.yehia.orderservice.model.request.OrderRequest;

public interface OrderService {
    void save(OrderRequest orderRequest);
}
