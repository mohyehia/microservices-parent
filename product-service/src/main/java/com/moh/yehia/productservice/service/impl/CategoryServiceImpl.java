package com.moh.yehia.productservice.service.impl;

import com.moh.yehia.productservice.model.dto.CategoryDTO;
import com.moh.yehia.productservice.model.entity.Category;
import com.moh.yehia.productservice.model.request.CategoryRequest;
import com.moh.yehia.productservice.model.response.CategoryRetrievalResponse;
import com.moh.yehia.productservice.repository.CategoryRepository;
import com.moh.yehia.productservice.service.design.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryRetrievalResponse finaAll() {
        List<Category> categories = categoryRepository.findAll();
        return new CategoryRetrievalResponse(categories.size(), populateCategoriesDTO(categories));
    }

    @Override
    public CategoryDTO save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return mapToCategoryDTO(categoryRepository.save(category));
    }

    private List<CategoryDTO> populateCategoriesDTO(List<Category> categories) {
        return categories.stream().map(this::mapToCategoryDTO).collect(Collectors.toList());
    }

    private CategoryDTO mapToCategoryDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getDescription());
    }
}
