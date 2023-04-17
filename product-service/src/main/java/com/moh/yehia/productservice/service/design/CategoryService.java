package com.moh.yehia.productservice.service.design;

import com.moh.yehia.productservice.model.dto.CategoryDTO;
import com.moh.yehia.productservice.model.entity.Category;
import com.moh.yehia.productservice.model.request.CategoryRequest;
import com.moh.yehia.productservice.model.response.CategoryRetrievalResponse;

public interface CategoryService {

    CategoryRetrievalResponse finaAll();

    CategoryDTO save(CategoryRequest categoryRequest);
}
