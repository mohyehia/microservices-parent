package com.moh.yehia.productservice.service.impl;

import com.moh.yehia.productservice.model.entity.Product;
import com.moh.yehia.productservice.model.request.ProductRequest;
import com.moh.yehia.productservice.model.response.ProductDTO;
import com.moh.yehia.productservice.repository.ProductRepository;
import com.moh.yehia.productservice.service.design.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(ProductRequest productRequest) {
        Product product = Product
                .builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .categoryId(productRequest.getCategoryId())
                .build();
        return productRepository.save(product);
    }

    @Override
    public List<ProductDTO> retrieveProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO mapToProductDTO(Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .build();
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

}
