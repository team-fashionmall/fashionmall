package com.fashionmall.coupon.entity;

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
    private Coupon couponId;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed; //쿠폰 사용 여부

    @Column(name = "used_at")
    private LocalDateTime usedAt; //쿠폰 사용 시간

    public UserCoupon(Long userId, Coupon couponId) {
        this.userId = userId;
        this.couponId = couponId;
        this.isUsed = false;
    }

    public void markAsUsed() {
        isUsed = true;
        usedAt = LocalDateTime.now();
    }
}
