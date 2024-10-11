package com.fashionmall.cart.repository;

import com.fashionmall.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByItemDetailIdAndUserId(Long itemDetailId, Long userId);
}
