package com.fashionmall.cart.repository;

import com.fashionmall.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByItemDetailIdAndUserId(Long itemDetailId, Long userId);
    Optional<Cart> findByIdAndUserId(Long cartId, Long userId);

    List<Cart> findByUserId(Long userId);
}
