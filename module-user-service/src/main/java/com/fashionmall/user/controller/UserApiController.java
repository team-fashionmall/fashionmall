package com.fashionmall.user.controller;

import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping ("/api/user/DeliveryAddressApi/{userId}")
    public CommonResponse<List<DeliveryAddressDto>> getUserDeliveryAddressApi (@PathVariable Long userId) {
        return ApiResponseUtil.success(userService.getDeliveryAddress(userId));
    }
}
