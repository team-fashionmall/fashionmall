package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @GetMapping("/cart")
    public CommonResponse<List<CartResponseDto>> getCartList() {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.getCartList(userId));
    }
}
