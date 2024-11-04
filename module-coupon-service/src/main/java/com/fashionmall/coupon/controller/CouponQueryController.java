package com.fashionmall.coupon.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.security.UserDetailsImpl;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponQueryController {

    private final CouponService couponService;

    //쿠폰목록조회
    @GetMapping("/coupon")
    public CommonResponse<PageInfoResponseDto<UserCouponResponseDto>> getUserCouponList(@RequestParam(defaultValue = "1") int pageNo,
                                                                                        @RequestParam(defaultValue = "10") int size,
                                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ApiResponseUtil.success(couponService.getUserCoupons(userDetails.getUserid(), pageNo, size));
    }

    //다운로드가능쿠폰목록조회
    @GetMapping("/coupon/download")
    public CommonResponse<List<UserCouponResponseDto>> getUserCouponDownloadList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(couponService.getDownloadableCoupons(userDetails.getUserid()));
    }

    //쿠폰다운로드
    @PostMapping("/coupon/download/{couponId}")
    public CommonResponse<Long> downloadCoupon(@PathVariable("couponId") Long couponId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(couponService.downloadCoupon(userDetails.getUserid(), couponId));
    }

}
