package com.fashionmall.coupon.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.CouponResponseDto;
import com.fashionmall.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CouponQueryController {

    private final CouponService couponService;

    //(관리자)쿠폰조회
    @GetMapping("/admin/coupon")
    public CommonResponse<PageInfoResponseDto<CouponResponseDto>> getCouponList(@RequestParam(defaultValue = "1") int page) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.getCoupons(page));
    }

    //(관리자)쿠폰등록
    @PostMapping("/admin/coupon")
    public CommonResponse<Long> publishCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.publishCoupon(couponRequestDto));
    }

    //(관리자)쿠폰수정
    @PutMapping("/admin/coupon/{couponId}")
    public CommonResponse<Long> updateCoupon(@PathVariable("couponId") Long couponId,
                                             @RequestBody CouponUpdateRequestDto couponUpdateRequestDto) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.updateCoupon(couponId, couponUpdateRequestDto));
    }

    //(관리자)쿠폰삭제
    @DeleteMapping("/admin/coupon/{couponId}")
    public CommonResponse<Long> deleteCoupon(@PathVariable("couponId") Long couponId) {
        //관리자 인증 관련 추가
        return ApiResponseUtil.success(couponService.deleteCoupon(couponId));
    }

    //쿠폰목록조회
    @GetMapping("/coupon")
    public CommonResponse<PageInfoResponseDto<CouponResponseDto>> getUserCouponList(@RequestParam(defaultValue = "1") int page) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.getUserCoupons(id, page));
    }

    //다운로드가능쿠폰목록조회
    @GetMapping("/coupon/download")
    public CommonResponse<PageInfoResponseDto<CouponResponseDto>> getUserCouponDownloadList(@RequestParam(defaultValue = "1") int page) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.getDownloadableCoupons(id, page));
    }

    //쿠폰다운로드
    @PostMapping("/coupon/download/{couponId}")
    public CommonResponse<Long> downloadCoupon(@PathVariable("couponId") Long couponId) {
        Long id = 1L; //임시부여
        return ApiResponseUtil.success(couponService.downloadCoupon(id, couponId));
    }

}
