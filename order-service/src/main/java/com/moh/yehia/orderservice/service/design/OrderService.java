package com.moh.yehia.orderservice.service.design;

import com.moh.yehia.orderservice.model.request.OrderRequest;

public interface OrderService {
    String save(OrderRequest orderRequest);
}
