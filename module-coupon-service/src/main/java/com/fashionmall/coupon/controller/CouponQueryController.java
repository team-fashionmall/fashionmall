package com.fashionmall.coupon.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponQueryController {

    private final CouponService couponService;

    //쿠폰목록조회
    @GetMapping("/coupon")
    public CommonResponse<PageInfoResponseDto<UserCouponResponseDto>> getUserCouponList(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.getUserCoupons(id, pageable));
    }

    //다운로드가능쿠폰목록조회
    @GetMapping("/coupon/download")
    public CommonResponse<PageInfoResponseDto<UserCouponResponseDto>> getUserCouponDownloadList(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.getDownloadableCoupons(id, pageable));
    }

    //쿠폰다운로드
    @PostMapping("/coupon/download/{couponId}")
    public CommonResponse<Long> downloadCoupon(@PathVariable("couponId") Long couponId) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.downloadCoupon(id, couponId));
    }

}
