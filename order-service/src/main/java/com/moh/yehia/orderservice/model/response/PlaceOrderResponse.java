package com.moh.yehia.orderservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse {
    private String status;
    private String message;
    private String orderNumber;
}
