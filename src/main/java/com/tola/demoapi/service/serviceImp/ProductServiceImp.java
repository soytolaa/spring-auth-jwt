package com.tola.demoapi.service.serviceImp;

import com.tola.demoapi.model.request.ProductRequest;
import com.tola.demoapi.model.response.ProductResponse;
import com.tola.demoapi.repository.ProductRepository;
import com.tola.demoapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> mapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse createProducts(ProductRequest productRequest) {
        return null;
    }
}
