package com.fashionmall.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailResponseDto {
    private Long id;
    private int totalPrice;
    private int discountPrice;
    private int paymentPrice;
    private String zipcode;
    private String roadAddress;
    private List<OrderItemDetailResponseDto> orderItemsDto;
}
