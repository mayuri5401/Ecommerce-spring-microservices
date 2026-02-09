package com.main.EcomCore.app.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductResponse  {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private  Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

}
