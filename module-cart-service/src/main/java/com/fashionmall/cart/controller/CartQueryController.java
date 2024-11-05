package com.fashionmall.cart.controller;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.security.UserDetailsImpl;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @GetMapping("/cart")
    public CommonResponse<List<CartResponseDto>> getCartList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(cartService.getCartList(userDetails.getUserid()));
    }
}
