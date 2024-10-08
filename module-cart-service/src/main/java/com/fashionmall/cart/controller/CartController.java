package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.request.CartRequestDto;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "cart controller")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @PostMapping("/cart")
    public CommonResponse<String> createCart (@Valid @RequestBody CartRequestDto cartRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.createCart(cartRequestDto, userId));
    }
}
