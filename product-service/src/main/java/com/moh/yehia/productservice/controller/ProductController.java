package com.moh.yehia.productservice.controller;

import com.moh.yehia.productservice.model.entity.Product;
import com.moh.yehia.productservice.model.request.ProductRequest;
import com.moh.yehia.productservice.model.response.ProductResponse;
import com.moh.yehia.productservice.service.design.NotificationService;
import com.moh.yehia.productservice.service.design.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final NotificationService notificationService;


    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.save(productRequest);
        notificationService.sendToInventory(product.getId());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> retrieveProducts() {
        return new ResponseEntity<>(productService.retrieveProducts(), HttpStatus.OK);
    }
}
