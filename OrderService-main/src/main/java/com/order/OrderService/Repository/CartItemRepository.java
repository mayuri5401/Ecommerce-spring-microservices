package com.order.OrderService.Repository;
// In `CartItemRepository.java`



import com.order.OrderService.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserIDAndProductId(String userID, String productId);
    void deleteByUserIDAndProductId(String userID, String productId);
    List<CartItem> findByUserID(String userID);
    void deleteByUserID(String userID);
}
