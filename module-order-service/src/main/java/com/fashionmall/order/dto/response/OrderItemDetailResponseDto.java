package com.fashionmall.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDetailResponseDto {

    private Long itemDetailId;
    private String itemDetailName;
    private int price;
    private int quantity;
    private int totalPrice;

    public OrderItemDetailResponseDto(Long itemDetailId, int price, int quantity, int totalPrice) {
        this.itemDetailId = itemDetailId;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
