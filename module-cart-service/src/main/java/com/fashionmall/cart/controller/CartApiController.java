package com.fashionmall.cart.controller;

import com.fashionmall.cart.service.CartService;
import com.fashionmall.common.moduleApi.dto.CartItemDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartApiController {

    private final CartService cartService;

    @GetMapping("/itemDetail/{userId}")
    public CommonResponse<List<ItemDetailDto>> getItemDetailFromCartApi(@PathVariable Long userId) {
        return ApiResponseUtil.success(cartService.getItemDetailFromCartApi(userId));
    }

    @GetMapping("/getIsSelectedItem/{userId}")
    public CommonResponse<List<CartItemDto>> getIsSelectedItemApi(@PathVariable Long userId) {
        return ApiResponseUtil.success(cartService.getIsSelectedItemApi(userId));
    }

    @DeleteMapping("/deleteIsSelectedItem/{userId}")
    public void deleteIsSelectedItem(@PathVariable Long userId, @RequestParam(name = "itemDetailIds") List<Long> itemDetailIds) {
        cartService.deleteOrderedItemApi(userId, itemDetailIds);
    }
}
