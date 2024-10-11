package com.fashionmall.order.dto.request;

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
    @JsonProperty("merchant_uid")
    private String merchantUid;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("pwd_2digit")
    private String pwd2digit;
    @JsonProperty("cart_quota")
    private int cardQuota;
    @JsonProperty("reason")
    private String cancelReason;

    public Payment toPayment(Orders orders, String impUid, LocalDateTime paidAt) {
        return Payment
                .builder()
                .userId(userId)
                .orders(orders)
                .impUid(impUid)
                .paidAt(paidAt)
                .merchantUid(merchantUid)
                .price(amount)
                .cardQuota(cardQuota)
                .build();
    }
}
