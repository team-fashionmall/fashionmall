package com.fashionmall.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    private Long itemDetailId;
    private String itemDetailName;
    private int quantity;
    private int price;
    private String imageUrl;

}
