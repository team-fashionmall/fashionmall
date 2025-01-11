package com.fashionmall.coupon.controller;

import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupon")
public class ApiCouponController {

    private final CouponService couponService;

    @GetMapping("/getCoupon/{userId}")
    public CommonResponse<List<CouponDto>> getCoupon(@PathVariable Long userId) {
        return ApiResponseUtil.success(couponService.getUserCouponsToApi(userId));
    }

    @PatchMapping("/useCoupon/{couponId}/{userId}")
    public void useCoupon(@PathVariable Long couponId, @PathVariable Long userId) {
        couponService.useCoupon(couponId, userId);
    }

    @PatchMapping("/cancelCoupon/{couponId}/{userId}")
    public void cancelCoupon(@PathVariable Long couponId, @PathVariable Long userId) {
        couponService.cancelCoupon(couponId, userId);
    }
}
