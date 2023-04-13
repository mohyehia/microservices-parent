package com.moh.yehia.orderservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineInquiry {
    private String productCode;
    private int quantity;
}
