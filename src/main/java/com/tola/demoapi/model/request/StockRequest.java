package com.tola.demoapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockRequest {
    private Integer stockId;
    private Integer stockQuantity;
    private Integer proId;
}
