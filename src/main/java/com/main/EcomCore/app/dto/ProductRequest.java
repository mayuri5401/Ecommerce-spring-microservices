package com.main.EcomCore.app.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private  Integer stockQuantity;
    private String category;
    private String imageUrl;

}
