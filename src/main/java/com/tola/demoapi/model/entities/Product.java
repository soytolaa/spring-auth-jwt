package com.tola.demoapi.model.entities;

import com.tola.demoapi.model.response.ProductResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proId;
    private String proName;
    private String proDes;
    private BigDecimal proPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "cat_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public ProductResponse toResponse() {
        return ProductResponse.builder().proId(proId).proName(proName).proDes(proDes).proPrice(proPrice)
                .catId(category.getCatId()).createdAt(LocalDateTime.now()).updatedAt(updatedAt).build();
    }
}
