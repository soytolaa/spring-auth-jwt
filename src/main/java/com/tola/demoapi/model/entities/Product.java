package com.tola.demoapi.model.entities;


import com.tola.demoapi.model.response.ProductResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proId;
    private String proName;
    private String proDes;
    private BigDecimal proPrice;


    public ProductResponse toResponse() {
        return ProductResponse.builder().proId(proId).proName(proName).proDes(proDes).proPrice(proDes).build();
    }
}
