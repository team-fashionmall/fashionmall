package com.fashionmall.order.dto.request;

import com.fashionmall.order.entity.OrderItem;
import com.fashionmall.order.entity.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrdersRequestDto {

    private Long userId;
    private Long couponId;
    // 직접 입력시 Null
    private Long deliveryAddressId;
    private Long billingKeyId;

    private String zipcode;
    private String roadAddress;

    private int totalPrice;
    private int discountPrice;
    private int paymentPrice;

    private List<OrderItemRequestDto> orderItemsDto;

    public Orders toOrders() {
        List<OrderItem> orderItems = orderItemsDto.stream()
                .map(orderitems -> new OrderItem(
                        orderitems.getItemDetailId(),
                        orderitems.getPrice(),
                        orderitems.getQuantity()
                ))
                .toList();

        return Orders
                .builder()
                .userId(userId)
                .couponId(couponId)
                .zipcode(zipcode)
                .roadAddress(roadAddress)
                .totalPrice(totalPrice)
                .discountPrice(discountPrice)
                .paymentPrice(paymentPrice)
                .orderItems(orderItems)
                .build();
    }
}
