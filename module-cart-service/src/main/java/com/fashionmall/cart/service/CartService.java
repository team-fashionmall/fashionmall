package com.fashionmall.cart.service;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;

import java.util.List;

public interface CartService {
    List<Long> createCart (CartRequestDto cartRequestDto, Long userId);
    CartUpdateResponseDto updateCart (Long cartId, CartUpdateRequestDto cartUpdateRequestDto, Long userId);
    String deleteCart(Long cartId, Long userId);

    List <ItemDetailDto> getItemDetailFromCartApi (Long userId);

}
