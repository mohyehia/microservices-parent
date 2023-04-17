package com.moh.yehia.productservice.repository;

import com.moh.yehia.productservice.model.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
