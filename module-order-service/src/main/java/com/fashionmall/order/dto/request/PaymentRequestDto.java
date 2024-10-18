package com.fashionmall.order.dto.request;

import com.fashionmall.order.entity.BillingKey;
import com.fashionmall.order.entity.Orders;
import com.fashionmall.order.entity.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentRequestDto {

    private Long userId;
    private Long orderId;
    private Long billingKeyId;
    @JsonProperty("customer_uid")
    private String customerUid;
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("amount")
    private int amount;

    //일회성 비인증
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("pwd_2digit")
    private String pwd2digit;
    private String birth;

    private String pg;
    @JsonProperty("cart_quota")
    private int cardQuota;
    @JsonProperty("reason")
    private String cancelReason;

    public Payment toPayment(Orders orders, BillingKey billingKey, String impUid, LocalDateTime paidAt) {
        return Payment
                .builder()
                .userId(userId)
                .orders(orders)
                .billingKey(billingKey)
                .impUid(impUid)
                .paidAt(paidAt)
                .merchantUid(merchantUid)
                .price(amount)
                .cardQuota(cardQuota)
                .build();
    }
}
