package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;

public interface CartService {
    String createCart (CartRequestDto cartRequestDto, Long userId, int price, String itemDetailName);
    CartUpdateResponseDto updateCart (Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId);
    String deleteCart(Long cartId, Long userId);
}
