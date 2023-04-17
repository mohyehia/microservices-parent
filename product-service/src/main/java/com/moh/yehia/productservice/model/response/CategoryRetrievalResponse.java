package com.moh.yehia.productservice.model.response;

import com.moh.yehia.productservice.model.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRetrievalResponse {
    private long size;
    private List<CategoryDTO> categories;
}
