package com.tola.demoapi.service;

import com.tola.demoapi.model.request.ProductRequest;
import com.tola.demoapi.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProducts();

    ProductResponse createProducts(ProductRequest productRequest);

    ProductResponse updateProduct(Integer id, ProductRequest productRequest);

    void deleteProduct(Integer id);

    ProductResponse getProductById(Integer id);

    List<ProductResponse> searchProduct(String keyword);
}
