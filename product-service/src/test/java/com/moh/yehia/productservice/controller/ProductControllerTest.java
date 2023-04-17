package com.moh.yehia.productservice.controller;

import com.moh.yehia.productservice.model.entity.Product;
import com.moh.yehia.productservice.model.request.ProductRequest;
import com.moh.yehia.productservice.model.response.ProductDTO;
import com.moh.yehia.productservice.service.design.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;

public class ProductControllerTest extends GlobalSpringContext {
    @Autowired
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = populateProductRequest();
        Product product = Product
                .builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        BDDMockito.given(productService.save(Mockito.any(ProductRequest.class))).willReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertEquals(1, productService.retrieveProducts().size());
    }

    @Test
    void shouldReturnProducts() throws Exception {
        Product product1 = populateProductEntity();
        Product product2 = populateProductEntity();
        BDDMockito.given(productService.retrieveProducts()).willReturn(Arrays.asList(mapToProductResponse(product1), mapToProductResponse(product2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private ProductRequest populateProductRequest() {
        return new ProductRequest(faker.commerce().productName(), faker.commerce().productName(), BigDecimal.valueOf(faker.number().randomNumber()));
    }

    private Product populateProductEntity() {
        return Product
                .builder()
                .name(faker.commerce().productName())
                .description(faker.commerce().productName())
                .price(BigDecimal.valueOf(faker.number().randomNumber()))
                .build();
    }

    private ProductDTO mapToProductResponse(Product product) {
        return ProductDTO
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
