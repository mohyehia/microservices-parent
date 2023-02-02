package com.moh.yehia.productservice.repository;

import com.moh.yehia.productservice.model.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
