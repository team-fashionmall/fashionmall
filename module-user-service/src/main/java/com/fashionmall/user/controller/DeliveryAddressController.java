package com.fashionmall.user.controller;

import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.entity.DeliveryAddress;
import com.fashionmall.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final UserService userService;

    @PostMapping("/address")
    public CommonResponse<Long> createDeliveryAddress (@Valid @RequestBody DeliveryAddressRequestDto deliveryAddressRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(userService.createDeliveryAddress(deliveryAddressRequestDto, userId));
    }
}
