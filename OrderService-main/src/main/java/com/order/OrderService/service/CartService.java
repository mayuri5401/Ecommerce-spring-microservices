package com.order.OrderService.service;


import com.order.OrderService.DTO.CartItemRequest;
import com.order.OrderService.Repository.CartItemRepository;
import com.order.OrderService.model.CartItem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    // private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    // private final UserRepository userRepository;

    public Boolean addToCart(String userId, CartItemRequest request) {
        log.info("Adding to cart - userId: {}, productId: {}, quantity: {}", userId, request.getProductId(), request.getQuantity());


        CartItem existingCartItem = cartItemRepository.findByUserIDAndProductId(userId, request.getProductId());

        if (existingCartItem != null) {
            log.info("Cart item exists, updating quantity");
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            log.info("Creating new cart item");
            CartItem cartItem = new CartItem();
            cartItem.setUserID(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
        }
        return true;

    }

    public boolean deleteItemFromCart(String userId, String productId) {
        log.info("Deleting from cart - userId: {}, productId: {}", userId, productId);

        // Optional<Product> productOpt = productRepository.findById(productId);
        // Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        // if(productOpt.isPresent() && userOpt.isPresent()){
        //     cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
        //     return true;
        // }
        CartItem cartItem = cartItemRepository.findByUserIDAndProductId(userId, productId);
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            log.info("Cart item deleted successfully");
            return  true;
        }
        log.warn("Cart item not found");
        return false;
    }

    public List<CartItem> getCart(String userId) {
        log.info("Retrieving cart for userId: {}", userId);
        List<CartItem> cartItems = cartItemRepository.findByUserID(userId);
        log.info("Found {} items in cart", cartItems.size());

        // return userRepository.findById(Long.valueOf(userId))
        //         .map(cartItemRepository::findByUser)
        //         .orElseGet(List::of);
        return cartItems;
    }

    public void clearCart(String userId) {
        log.info("Clearing cart for userId: {}", userId);
        // userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
        cartItemRepository.deleteByUserID(userId);
        log.info("Cart cleared");
    }
}


