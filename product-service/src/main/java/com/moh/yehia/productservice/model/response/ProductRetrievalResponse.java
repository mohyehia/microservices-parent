package com.moh.yehia.productservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRetrievalResponse {
    private long size;
    private List<ProductDTO> products;
}
