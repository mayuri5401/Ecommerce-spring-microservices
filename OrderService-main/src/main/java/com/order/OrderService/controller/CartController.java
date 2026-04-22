package com.order.OrderService.controller;


import com.order.OrderService.DTO.CartItemRequest;
import com.order.OrderService.model.CartItem;
import com.order.OrderService.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/cart")
@RestController
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }
            if (request == null || request.getProductId() == null || request.getProductId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Product ID is required");
            }
            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                return ResponseEntity.badRequest().body("Quantity must be greater than 0");
            }
            // Validate userId format
            Long.valueOf(userId);
            if (!cartService.addToCart(userId, request)) {
                return ResponseEntity.badRequest().body("Product Out of stock or User Not found");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid User ID format");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error: " + e.getMessage());
        }
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId) {
        try {
            if (userId == null || userId.trim().isEmpty() || productId == null || productId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            boolean deleted = cartService.deleteItemFromCart(userId, productId);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId) {
        try {
            if (userId == null || userId.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            // Validate userId format
            Long.valueOf(userId);
            List<CartItem> cartItems = cartService.getCart(userId);
            return ResponseEntity.ok(cartItems);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


}