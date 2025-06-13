package com.tola.demoapi.service;

import com.tola.demoapi.model.request.CategoryRequest;
import com.tola.demoapi.model.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest);

    CategoryResponse getCategoryById(Integer id);
}
