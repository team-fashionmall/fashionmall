package com.fashionmall.user.controller;

import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.ItemInfoResponseDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.common.util.ProfileUtil;
import com.fashionmall.user.dto.response.UserInfoResponseDto;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserQueryController {

    private final UserService userService;
    private final ProfileUtil profileUtil;

    // User
    @GetMapping("/user/info")
    public CommonResponse<UserInfoResponseDto> userInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.userInfo(userDetails.getUserid()));
    }

    // DeliveryAddress
    @GetMapping("/address")
    public CommonResponse<List<DeliveryAddressDto>> getDeliveryAddress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.getDeliveryAddress(userDetails.getUserid()));
    }

    // Favorite
    @GetMapping("/favorite")
    public CommonResponse<PageInfoResponseDto<ItemInfoResponseDto>> favoriteList(@RequestParam(defaultValue = "1") int pageNo,
                                                                                 @RequestParam(defaultValue = "8") int size,
                                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.favoriteList(pageNo, size, userDetails.getUserid()));
    }

    @GetMapping("/user/profile")
    public String getCartProfile() {
        return profileUtil.getCurrentProfile();
    }
}
