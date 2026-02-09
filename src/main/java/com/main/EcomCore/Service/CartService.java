package com.main.EcomCore.Service;

import com.main.EcomCore.Model.CartItem;
import com.main.EcomCore.Model.Product;
import com.main.EcomCore.Model.User;
import com.main.EcomCore.Repostitory.CartItemRepository;
import com.main.EcomCore.Repostitory.ProductRepository;
import com.main.EcomCore.Repostitory.UserRepository;
import com.main.EcomCore.app.dto.CartItemRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public Boolean addToCart (String userId, CartItemRequest request) {
        //Look for product
        Optional<Product> productopt = productRepository.findById(request.getProductId());
        if (productopt.isEmpty()) {
            return false;
        }

        Product product = productopt.get();
        if (product.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
        if (useropt.isEmpty()) {
            return false;
        }
        User user = useropt.get();

        CartItem exisitingCartItem = cartItemRepository.findByUserAndProduct(user, product);


        if (exisitingCartItem != null) {
            exisitingCartItem.setQuantity(exisitingCartItem.getQuantity() + request.getQuantity());
            exisitingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(exisitingCartItem.getQuantity())));
            cartItemRepository.save(exisitingCartItem);
        } else {
            // Only use request.getQuantity() when exisitingCartItem is null
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;

    }

    public boolean deleteItemFromCart (String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);


        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(productOpt.isPresent() && userOpt.isPresent()){
            cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
            return true;
        }
        return  false;
    }
    public List<CartItem>getCart(String userId){
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }


}
