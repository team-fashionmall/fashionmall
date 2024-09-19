package com.fashionmall.order.entity;

import com.fashionmall.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Orders extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Column(name = "payment_price", nullable = false)
    private int paymentPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;
}
