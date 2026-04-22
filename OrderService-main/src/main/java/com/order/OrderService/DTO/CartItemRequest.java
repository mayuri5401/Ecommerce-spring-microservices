package com.order.OrderService.DTO;

import lombok.Data;

@Data
public class CartItemRequest {
    private  String productId;
    private  Integer quantity;
}
