package com.fashionmall.order.dto.response;

import com.fashionmall.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersDetailResponseDto {
    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int couponDiscountPrice;
    private int totalItemDiscountPrice;
    private int paymentPrice;
    private String zipcode;
    private String roadAddress;
    private List<OrderItemDetailResponseDto> orderItemsDto;

    public OrdersDetailResponseDto(Long id, OrderStatus orderStatus, int totalPrice, int couponDiscountPrice, int totalItemDiscountPrice, int paymentPrice, String zipcode, String roadAddress) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.couponDiscountPrice = couponDiscountPrice;
        this.totalItemDiscountPrice = totalItemDiscountPrice;
        this.paymentPrice = paymentPrice;
        this.zipcode = zipcode;
        this.roadAddress = roadAddress;
    }
}