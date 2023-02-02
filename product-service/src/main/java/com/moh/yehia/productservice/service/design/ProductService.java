package com.moh.yehia.productservice.service.design;

import com.moh.yehia.productservice.model.entity.Product;
import com.moh.yehia.productservice.model.request.ProductRequest;
import com.moh.yehia.productservice.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    Product save(ProductRequest productRequest);

    List<ProductResponse> retrieveProducts();
}
