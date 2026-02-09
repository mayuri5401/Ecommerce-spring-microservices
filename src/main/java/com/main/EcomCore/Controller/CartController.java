package com.main.EcomCore.Controller;

import com.main.EcomCore.Model.CartItem;
import com.main.EcomCore.Service.CartService;
import com.main.EcomCore.app.dto.CartItemRequest;
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
            Long userIdLong = Long.valueOf(userId);
            if (!cartService.addToCart(String.valueOf(userIdLong), request)) {
                return ResponseEntity.badRequest().body("Product Out of stock or User Not found");
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid User ID format");
        }
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @GetMapping()
    public  ResponseEntity<List<CartItem>>getCart( @RequestHeader("X-User-ID") String userId){
        return  ResponseEntity.ok(cartService.getCart(userId));
    }


}