package com.moh.yehia.productservice.controller;

import com.moh.yehia.productservice.model.dto.CategoryDTO;
import com.moh.yehia.productservice.model.request.CategoryRequest;
import com.moh.yehia.productservice.model.response.CategoryRetrievalResponse;
import com.moh.yehia.productservice.service.design.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CategoryRetrievalResponse> retrieveCategories() {
        return new ResponseEntity<>(categoryService.finaAll(), HttpStatus.OK);
    }
}
