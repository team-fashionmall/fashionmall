package com.fashionmall.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailResponseDto {
    private Long id;
    private int totalPrice;
    private int couponDiscountPrice;
    private int totalItemDiscountPrice;
    private int paymentPrice;
    private String zipcode;
    private String roadAddress;
    private List<OrderItemDetailResponseDto> orderItemsDto;

    public OrdersDetailResponseDto(Long id, int totalPrice, int couponDiscountPrice, int totalItemDiscountPrice, int paymentPrice, String zipcode, String roadAddress, List<OrderItemDetailResponseDto> orderItemsDto) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.couponDiscountPrice = couponDiscountPrice;
        this.totalItemDiscountPrice = totalItemDiscountPrice;
        this.paymentPrice = paymentPrice;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
        this.orderItemsDto = orderItemsDto;
    }
}
