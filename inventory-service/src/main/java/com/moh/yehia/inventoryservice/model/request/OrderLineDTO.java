package com.moh.yehia.inventoryservice.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderLineDTO {
    @NotEmpty
    private String productCode;

    @NotNull
    private BigDecimal price;

    @Min(1)
    private int quantity;
}
