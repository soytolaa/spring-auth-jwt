package com.tola.demoapi.model.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Integer proId;
    private String proName;
    private String proDes;
    private String proPrice;
}
