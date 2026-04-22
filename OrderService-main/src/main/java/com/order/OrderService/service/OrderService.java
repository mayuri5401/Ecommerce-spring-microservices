package com.order.OrderService.service;


import com.order.OrderService.DTO.OrderItemDTO;
import com.order.OrderService.DTO.OrderResponse;
import com.order.OrderService.Repository.OrderRepository;
import com.order.OrderService.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
//    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Optional<Object> createOrder (String userId) {
//        Validate for cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
////        validate for user
//        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
//        if (userOptional.isEmpty()) {
//            return Optional.empty();
//        }
//        User user = userOptional.get();

//        calculate the total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem
                        ::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

//        create order
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderstatus(Orderstatus.CONFIRM);
        order.setTotalAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItem(
                        null,
                        cartItem.getProductId(),
                        cartItem.getQuantity(),
                        cartItem.getPrice(),
                        order

                )).toList();
        order.setItems(orderItems);
        Order saveOrder = orderRepository.save(order);


        //        clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(saveOrder));
    }

    private OrderResponse mapToOrderResponse (Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getOrderstatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        )).toList(),
                order.getCreatedAt()
        );
    }

}


