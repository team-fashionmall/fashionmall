package com.fashionmall.order.event;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;

import java.util.List;

public class StockDeductEvent {

    private final Long ordersId;
    private final List<OrderItemDto> orderItems;

    public StockDeductEvent(Long ordersId, List<OrderItemDto> orderItems) {
        this.ordersId = ordersId;
        this.orderItems = orderItems;
    }

    public Long getOrdersId() {
        return ordersId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
}