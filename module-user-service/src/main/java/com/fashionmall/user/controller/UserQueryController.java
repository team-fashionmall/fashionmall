package com.fashionmall.user.controller;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.response.DeliveryAddressResponseDto;
import com.fashionmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserQueryController {

    private final UserService userService;

    // DeliveryAddress
    @GetMapping("/address")
    public CommonResponse<List<DeliveryAddressResponseDto>> getDeliveryAddress () {
        Long userId = 1L;
        return ApiResponseUtil.success(userService.getDeliveryAddress(userId));
    }

    // Favorite
    @GetMapping ("/favorite/{itemId}")
    public CommonResponse<PageInfoResponseDto<LikeItemListResponseDto>> favoriteList (@RequestParam(defaultValue = "1") int pageNo,
                                                                                      @RequestParam(defaultValue = "8") int size,
                                                                                      @PathVariable Long itemId) {
        Long userId = 1L;
        return ApiResponseUtil.success(userService.favoriteList(pageNo, size, itemId, userId));
    }
}
