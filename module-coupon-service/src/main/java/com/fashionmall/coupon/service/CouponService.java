package com.fashionmall.coupon.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import org.springframework.data.domain.Pageable;

public interface CouponService {

    PageInfoResponseDto<AdminCouponResponseDto> getCoupons(Pageable pageable);

    Long publishCoupon(CouponRequestDto couponRequestDto);

    Long updateCoupon(CouponUpdateRequestDto couponUpdateRequestDto);

    Long deleteCoupon(Long couponId);

    PageInfoResponseDto<UserCouponResponseDto> getUserCoupons(Long userId, Pageable pageable);

    PageInfoResponseDto<UserCouponResponseDto> getDownloadableCoupons(Long userId, Pageable pageable);

    Long downloadCoupon(Long userId, Long couponId);
}
