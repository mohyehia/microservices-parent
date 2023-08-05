package com.moh.yehia.inventoryservice.model.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderRequest {
    @Valid
    @NotNull
    private List<OrderLineDTO> orderLines;
}
