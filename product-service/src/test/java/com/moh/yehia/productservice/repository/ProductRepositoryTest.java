package com.moh.yehia.productservice.repository;

import com.moh.yehia.productservice.model.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Arrays;

@Testcontainers
@DataMongoTest
public class ProductRepositoryTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testSaveProduct() {
        Product product = Product
                .builder()
                .name("product name")
                .description("product description")
                .price(BigDecimal.valueOf(1000))
                .build();
        Product savedProduct = productRepository.save(product);
        Assertions.assertThat(savedProduct).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(product);
    }

    @Test
    void testFindAllProducts() {
        Product product1 = Product
                .builder()
                .name("product name 01")
                .description("product description")
                .price(BigDecimal.valueOf(1000))
                .build();
        Product product2 = Product
                .builder()
                .name("product name 02")
                .description("product description")
                .price(BigDecimal.valueOf(1000))
                .build();
        productRepository.saveAll(Arrays.asList(product1, product2));
        Assertions.assertThat(productRepository.findAll()).hasSize(2);
    }
}
