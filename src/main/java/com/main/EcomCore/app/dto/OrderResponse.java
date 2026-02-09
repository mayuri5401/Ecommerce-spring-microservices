package com.main.EcomCore.app.dto;

import com.main.EcomCore.Model.Orderstatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private Orderstatus orderstatus;
    private List<OrderItemDTO> items;
    private LocalDateTime createAt;
}
