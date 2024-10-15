package com.fashionmall.order.dto.response;

import com.fashionmall.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrdersResponseDto {

    private Long ordersId;
    private OrderStatus orderStatus;
    private int paymentPrice;
    private LocalDateTime createAt;
    private String itemImage;
    private String itemName;

    public OrdersResponseDto(Long ordersId, OrderStatus orderStatus, int paymentPrice, LocalDateTime createAt) {
        this.ordersId = ordersId;
        this.orderStatus = orderStatus;
        this.paymentPrice = paymentPrice;
        this.createAt = createAt;
    }
}
