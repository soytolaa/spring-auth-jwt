package com.tola.demoapi.model.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
        private String proName;
        private String proDes;
        private BigDecimal proPrice;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Integer catId;
}
