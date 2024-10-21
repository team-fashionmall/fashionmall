package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.dto.request.CartUpdateRequestDto;
import com.fashionmall.cart.dto.response.CartUpdateResponseDto;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "cart controller")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public CommonResponse<List<Long>> createCart (@Valid @RequestBody CartRequestDto cartRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.createCart(cartRequestDto, userId));
    }

    @PatchMapping ("/cart/{cartId}")
    public CommonResponse <CartUpdateResponseDto> updateCart (@PathVariable  Long cartId,
                                                              @Valid @RequestBody CartUpdateRequestDto cartUpdateRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.updateCart(cartId, cartUpdateRequestDto, userId));
    }

    @DeleteMapping ("/cart/{cartId}")
    public CommonResponse <String> deleteCart (@PathVariable Long cartId) {
        Long userId=1L;
        return ApiResponseUtil.success(cartService.deleteCart(cartId, userId));
    }
}
