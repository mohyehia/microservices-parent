//package com.moh.yehia.productservice.service;
//
//import com.github.javafaker.Faker;
//import com.moh.yehia.productservice.model.entity.Product;
//import com.moh.yehia.productservice.model.request.ProductRequest;
//import com.moh.yehia.productservice.model.response.ProductDTO;
//import com.moh.yehia.productservice.service.design.ProductService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//public class ProductServiceTest {
//    @Mock
//    private ProductService productService;
//
//    private static Faker faker;
//
//    @BeforeAll
//    static void initializeFaker() {
//        faker = new Faker(Locale.ENGLISH);
//    }
//
//    @Test
//    @DisplayName("test retrieving all products")
//    void when_calling_retrieveProducts_then_return_all_products() {
//        Product product1 = populateProduct();
//        Product product2 = populateProduct();
//        List<ProductDTO> productsResponse = Arrays.asList(mapToProductResponse(product1), mapToProductResponse(product2));
//        BDDMockito.given(productService.retrieveProducts()).willReturn(productsResponse);
//        Assertions.assertThat(productService.retrieveProducts())
//                .hasSize(2)
//                .doesNotContainNull();
//    }
//
//    @Test
//    @DisplayName("test saving new product")
//    void when_calling_save_function_then_product_is_saved() {
//        ProductRequest productRequest = populateProductRequest();
//        BDDMockito.given(productService.save(Mockito.any(ProductRequest.class))).willReturn(productFromProductRequest(productRequest));
//        Product savedProduct = productService.save(productRequest);
//        Assertions.assertThat(savedProduct.getName()).isEqualTo(productRequest.getName());
//    }
//
//
//    private Product populateProduct() {
//        return Product
//                .builder()
//                .id(UUID.randomUUID().toString())
//                .name(faker.commerce().productName())
//                .description(faker.commerce().material())
//                .price(BigDecimal.valueOf(faker.number().randomNumber()))
//                .build();
//    }
//
//    private ProductDTO mapToProductResponse(Product product) {
//        return ProductDTO
//                .builder()
//                .id(product.getId())
//                .name(product.getName())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .build();
//    }
//
//    private ProductRequest populateProductRequest() {
//        return new ProductRequest(faker.commerce().productName(), faker.commerce().material(), BigDecimal.valueOf(faker.number().randomNumber()), faker.commerce().department());
//    }
//
//    private Product productFromProductRequest(ProductRequest productRequest) {
//        return Product
//                .builder()
//                .id(UUID.randomUUID().toString())
//                .name(productRequest.getName())
//                .description(productRequest.getDescription())
//                .price(productRequest.getPrice())
//                .categoryId(productRequest.getCategoryId())
//                .build();
//    }
//}
