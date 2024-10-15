package com.fashionmall.order.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.OrdersRequestDto;
import com.fashionmall.order.dto.response.OrdersCompleteResponseDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;

public interface OrdersService {

    Long createOrder(OrdersRequestDto ordersRequestDto);

    OrdersCompleteResponseDto completeOrder(Long orderId);

    PageInfoResponseDto<OrdersResponseDto> getOrders(Long userId, int pageNo, int size);

    OrdersDetailResponseDto getOrderDetail(Long userId, Long orderId);

    Long cancelOrder(Long userId, Long orderId);
}
