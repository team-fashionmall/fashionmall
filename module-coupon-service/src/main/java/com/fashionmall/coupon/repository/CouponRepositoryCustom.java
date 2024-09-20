package com.fashionmall.coupon.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.response.AdminCouponResponseDto;
import com.fashionmall.coupon.dto.response.UserCouponResponseDto;
import org.springframework.data.domain.Pageable;

public interface CouponRepositoryCustom {

    PageInfoResponseDto<AdminCouponResponseDto> couponListPaged(Pageable pageable);

    PageInfoResponseDto<UserCouponResponseDto> findUserCouponByUserId(Long userId, Pageable pageable);

    PageInfoResponseDto<UserCouponResponseDto> findDownloadableCoupon(Long userId, Pageable pageable);

}
