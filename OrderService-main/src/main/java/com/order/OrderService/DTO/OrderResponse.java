package com.order.OrderService.DTO;

import com.order.OrderService.model.Orderstatus;
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
