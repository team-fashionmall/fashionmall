package com.fashionmall.order.event;

public class CouponCancelEvent {

    private final Long couponId;
    private final Long userId;

    public CouponCancelEvent(Long couponId, Long userId) {
        this.couponId = couponId;
        this.userId = userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public Long getUserId() {
        return userId;
    }
}