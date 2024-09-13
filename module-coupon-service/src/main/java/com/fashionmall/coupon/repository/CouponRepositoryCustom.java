package com.fashionmall.coupon.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.CouponResponseDto;

public interface CouponRepositoryCustom {

    PageInfoResponseDto<CouponResponseDto> couponListPaged(int pageNo);

    PageInfoResponseDto<CouponResponseDto> findUserCouponByUserId(Long userId, int pageNo);

    PageInfoResponseDto<CouponResponseDto> findDownloadableCoupon(Long userId, int pageNo);
}
