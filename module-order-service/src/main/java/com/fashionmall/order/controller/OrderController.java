package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.OrderPaymentRequestDto;
import com.fashionmall.order.dto.request.PaymentCancelRequestDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import com.fashionmall.order.service.OrdersService;
import com.fashionmall.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    public CommonResponse<Long> createOrderAndPayment(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto) {

        return ApiResponseUtil.success(ordersService.createAndPaymentOrder(orderPaymentRequestDto));
    }

    @GetMapping("/order")
    public CommonResponse<PageInfoResponseDto<OrdersResponseDto>> getUserOrderList(int pageNo, int size) {
        Long userId = 1L;
        return ApiResponseUtil.success(ordersService.getOrders(userId, pageNo, size));
    }

    @GetMapping("/order/{ordersId}")
    public CommonResponse<OrdersDetailResponseDto> getOrderDetail(@PathVariable Long ordersId) {
        Long userId = 1L;
        return ApiResponseUtil.success(ordersService.getOrderDetail(userId, ordersId));
    }

    @PatchMapping("/order/{orderId}/cancel")
    public CommonResponse<Long> cancelOrder(@PathVariable Long orderId,
                                            @RequestBody PaymentCancelRequestDto paymentCancelRequestDto) {
        Long userId = 1L;

        paymentCancelRequestDto.setUserId(userId);
        paymentCancelRequestDto.setOrderId(orderId);
        paymentService.cancelPayment(paymentCancelRequestDto);
        return ApiResponseUtil.success(ordersService.cancelOrder(userId, orderId));
    }
}