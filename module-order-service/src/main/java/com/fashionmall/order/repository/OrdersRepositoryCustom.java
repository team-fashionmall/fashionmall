package com.fashionmall.order.repository;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.response.OrdersCompleteResponseDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdersRepositoryCustom {

    OrdersCompleteResponseDto findByOrderId(Long orderId);

    List<OrderItemDto> findOrderItemsByOrderId(Long orderId);

    PageInfoResponseDto<OrdersResponseDto> findOrdersByUserId(Long userId, Pageable pageable);

    OrdersDetailResponseDto findOrdersDetailsByUserIdAndOrderId(Long userId, Long orderId);

    Long cancelOrderById(Long userId, Long orderId);
}

