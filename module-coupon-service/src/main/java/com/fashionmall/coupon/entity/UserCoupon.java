package com.fashionmall.coupon.entity;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_coupon")
public class UserCoupon extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_coupon_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupons couponId;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed = false; //쿠폰 사용 여부

    @Column(name = "used_at")
    private LocalDateTime usedAt; //쿠폰 사용 시간

    public UserCoupon(Long userId, Coupons couponId) {
        this.userId = userId;
        this.couponId = couponId;
    }

    public void UseCoupon() {
        if (isUsed) {
            throw new CustomException(ErrorResponseCode.ORDER_NOT_FOUND_COUPON);
        }
        isUsed = true;
        usedAt = LocalDateTime.now();
    }
}
