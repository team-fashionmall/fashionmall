package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;

public interface CartService {
    String createCart (CartRequestDto cartRequestDto, Long userId);
}
