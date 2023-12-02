package com.moh.yehia.productservice.controller;

import com.moh.yehia.productservice.model.dto.CategoryDTO;
import com.moh.yehia.productservice.model.request.CategoryRequest;
import com.moh.yehia.productservice.model.response.CategoryRetrievalResponse;
import com.moh.yehia.productservice.service.design.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(Authentication authentication, @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("authentication =>{}", authentication.toString());
        return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CategoryRetrievalResponse> retrieveCategories() {
        return new ResponseEntity<>(categoryService.finaAll(), HttpStatus.OK);
    }
}
