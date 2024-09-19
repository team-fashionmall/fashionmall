package com.fashionmall.order.entity;

import com.fashionmall.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_key_id", nullable = false)
    private BillingKey billingKey;

    @Column(name = "merchant_uid")
    private String merchantUid; //고객사 주문번호

    @Column(name = "card_quota")
    private int cardQuota; //할부개월 수

    @Column(name = "imp_uid")
    private String impUid; //포트원 거래고유번호

    @Column(name = "price")
    private int price; //결제 금액

    @Column(name = "cancel_price")
    private int cancelPrice; //취소 금액

    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt; //결제 시각

    @Column(name = "canceled_at")
    private LocalDateTime canceled_at; //결제 취소 시각

    @Column(name = "cancel_reason")
    private String cancelReason; //결제 취소 사유
}
