package com.fashionmall.cart.controller;

//import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CartQueryController {

    private final CartService cartService;

    @GetMapping("/cart")
    public CommonResponse<PageInfoResponseDto<CartResponseDto>> getCartList(@RequestParam(defaultValue = "1") int pageNo,
                                                                            @RequestParam(defaultValue = "10") int size) {
        Long userId = 1L;
        return ApiResponseUtil.success(cartService.getCartList(pageNo, size, userId));
    }
}
