package com.moh.yehia.productservice.controller;

import com.moh.yehia.productservice.exception.InvalidRequestException;
import com.moh.yehia.productservice.model.entity.Product;
import com.moh.yehia.productservice.model.request.ProductRequest;
import com.moh.yehia.productservice.model.response.ProductDTO;
import com.moh.yehia.productservice.model.response.ProductRetrievalResponse;
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
    public ResponseEntity<ProductDTO> saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.save(productRequest);
//        notificationService.sendToInventory(product.getId());
        return new ResponseEntity<>(productService.mapToProductDTO(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ProductRetrievalResponse> retrieveProducts(@RequestParam(value = "category", required = false) String categoryId) {
        List<ProductDTO> productDTOS;
        if (categoryId != null && !categoryId.isEmpty()){
            productDTOS = productService.findByCategoryId(categoryId);
        }else{
            productDTOS  = productService.retrieveProducts();
        }
        return new ResponseEntity<>(new ProductRetrievalResponse(productDTOS.size(), productDTOS), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable("id") String id) {
        Product product = productService.findById(id);
        if (product == null) {
            throw new InvalidRequestException("Product not found. please check the product id and try again!");
        }
        return new ResponseEntity<>(productService.mapToProductDTO(product), HttpStatus.OK);
    }
}
