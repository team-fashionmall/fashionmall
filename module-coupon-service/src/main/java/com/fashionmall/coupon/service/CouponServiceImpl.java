package com.fashionmall.coupon.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.coupon.dto.request.CouponRequestDto;
import com.fashionmall.coupon.dto.request.CouponUpdateRequestDto;
import com.fashionmall.coupon.dto.response.CouponResponseDto;
import com.fashionmall.coupon.entity.Coupon;
import com.fashionmall.coupon.entity.UserCoupon;
import com.fashionmall.coupon.enums.CouponStatus;
import com.fashionmall.coupon.enums.DiscountType;
import com.fashionmall.coupon.repository.CouponRepository;
import com.fashionmall.coupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Override
    public PageInfoResponseDto<CouponResponseDto> getCoupons(int page) {
        return couponRepository.couponListPaged(page);
    }

    @Override
    public Long publishCoupon(CouponRequestDto couponRequestDto) {
        Coupon coupon = couponRequestDto.toEntity();
        validateMaxDiscountPrice(coupon);
        Coupon saveCoupon = couponRepository.save(coupon);
        return saveCoupon.getId();
    }

    @Override
    @Transactional
    public Long updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

        boolean hasUpdated = false;

        if (StringUtils.hasText(couponUpdateRequestDto.getName())) {
            couponRepository.findByName(couponUpdateRequestDto.getName())
                    .ifPresent(c -> {
                        throw new CustomException(ErrorResponseCode.DUPLICATE_COUPON_NAME);
                    });
            coupon.updateName(couponUpdateRequestDto.getName());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getDiscountType() != null) {
            coupon.updateDiscountType(couponUpdateRequestDto.getDiscountType());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getDiscountValue() > 0) {
            coupon.updateDiscountValue(couponUpdateRequestDto.getDiscountValue());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getStartDate() != null) {
            coupon.updateStartDate(couponUpdateRequestDto.getStartDate());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getEndDate() != null && couponUpdateRequestDto.getEndDate().isAfter(LocalDateTime.now())) {
            coupon.updateEndDate(couponUpdateRequestDto.getEndDate());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getMinPurchasePrice() > 0) {
            coupon.updateMinPurchasePrice(couponUpdateRequestDto.getMinPurchasePrice());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getMaxDiscountPrice() != null) {
            validateMaxDiscountPrice(coupon);
            coupon.updateMaxDiscountPrice(couponUpdateRequestDto.getMaxDiscountPrice());
            hasUpdated = true;
        }

        if (couponUpdateRequestDto.getStatus() != null) {
            coupon.updateStatus(couponUpdateRequestDto.getStatus());
            hasUpdated = true;
        }

        if (!hasUpdated) {
            throw new CustomException(ErrorResponseCode.BAD_REQUEST);
        }

        return coupon.getId();
    }

    @Override
    @Transactional
    public Long deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
        return couponId;
    }

    @Override
    public PageInfoResponseDto<CouponResponseDto> getUserCoupons(Long userId, int page) {
        return couponRepository.findUserCouponByUserId(userId, page);
    }

    @Override
    public PageInfoResponseDto<CouponResponseDto> getDownloadableCoupons(Long userId, int page) {
        return couponRepository.findDownloadableCoupon(userId, page);
    }

    @Override
    @Transactional
    public Long downloadCoupon(Long userId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));
        validateMaxDiscountPrice(coupon);
        UserCoupon userCoupon = new UserCoupon(userId, coupon);
        UserCoupon saveUserCoupon = userCouponRepository.save(userCoupon);
        return saveUserCoupon.getId();
    }

    private void validateMaxDiscountPrice(Coupon coupon) {
        if (coupon.getDiscountType() == DiscountType.RATE && (coupon.getMaxDiscountPrice() == null || coupon.getMaxDiscountPrice() <= 0)) {
            throw new CustomException(ErrorResponseCode.BAD_REQUEST);
        }
        if (coupon.getDiscountType() == DiscountType.AMOUNT) {
            coupon.setMaxDiscountPriceNull();
        }
    }

    private void validateCouponDownloadable(Long userId, Coupon coupon) {
        if (coupon.getStatus() != CouponStatus.ACTIVATED) {
            throw new CustomException(ErrorResponseCode.ORDER_NOT_FOUND_COUPON);
        }

        boolean alreadyDownloadable = userCouponRepository.existsByUserIdAndCouponId(userId, coupon.getId());
        if (!alreadyDownloadable) {
            throw new CustomException(ErrorResponseCode.ORDER_NOT_FOUND_COUPON);
        }
    }
}
