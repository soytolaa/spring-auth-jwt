package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.exception.NotFoundException;
import com.tola.demoapi.model.entities.Product;
import com.tola.demoapi.model.request.ProductRequest;
import com.tola.demoapi.model.response.ProductResponse;
import com.tola.demoapi.repository.ProductRepository;
import com.tola.demoapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.tola.demoapi.model.entities.Category;
import com.tola.demoapi.repository.CategoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProducts(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCatId())
                .orElseThrow(() -> new NotFoundException("Category not found!"));
        Product product = mapper.map(productRequest, Product.class);
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product).toResponse();
    }

    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found!"));
        mapper.map(productRequest, product);
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product).toResponse();
    }

    @Override
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found!"));
        productRepository.delete(product);

    }

    @Override
    public ProductResponse getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found!"))
                .toResponse();
    }

    @Override
    public List<ProductResponse> searchProduct(String keyword) {
        List<Product> products = productRepository.findByProNameContaining(keyword);
        if (products.isEmpty()) {
            throw new NotFoundException("No product found with name: " + keyword);
        }
        return products.stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
}
