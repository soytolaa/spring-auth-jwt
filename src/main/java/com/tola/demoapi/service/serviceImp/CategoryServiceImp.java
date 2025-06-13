package com.tola.demoapi.service.serviceImp;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import com.tola.demoapi.repository.CategoryRepository;
import com.tola.demoapi.service.CategoryService;
import com.tola.demoapi.model.response.CategoryResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import com.tola.demoapi.model.entities.Category;
import com.tola.demoapi.model.request.CategoryRequest;
import com.tola.demoapi.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> {
            CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
            if (category.getProducts().isEmpty()) {
                categoryResponse.setProducts(Collections.emptyList());
            }
            return categoryResponse;
        })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setProducts(Collections.emptyList());
        return modelMapper.map(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        category.setCatName(categoryRequest.getCatName());
        category.setCatDes(categoryRequest.getCatDes());
        category.setUpdatedAt(LocalDateTime.now());
        return modelMapper.map(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        return modelMapper.map(
                categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found!")),
                CategoryResponse.class);
    }
}
