package com.fashionmall.coupon.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.CouponResponseDto;

public interface CouponService {

    PageInfoResponseDto<CouponResponseDto> getCoupons(int page);

    Long publishCoupon(CouponRequestDto couponRequestDto);

    Long updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto);

    Long deleteCoupon(Long couponId);

    PageInfoResponseDto<CouponResponseDto> getUserCoupons(Long userId, int page);

    PageInfoResponseDto<CouponResponseDto> getDownloadableCoupons(Long userId, int page);

    Long downloadCoupon(Long userId, Long couponId);
}
