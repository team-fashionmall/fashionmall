package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.OrdersRequestDto;
import com.fashionmall.order.dto.response.OrdersCompleteResponseDto;
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
    public CommonResponse<Long> createOrder(@RequestBody OrdersRequestDto ordersRequestDto) {

        return ApiResponseUtil.success(ordersService.createOrder(ordersRequestDto));
    }

    @GetMapping("/order/complete/{ordersId}")
    public CommonResponse<OrdersCompleteResponseDto> completeOrder(@PathVariable Long ordersId) {
        return ApiResponseUtil.success(ordersService.completeOrder(ordersId));
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

    @PatchMapping("/order/{orderId}")
    public CommonResponse<Long> cancelOrder(@PathVariable Long orderId) {
        Long userId = 1L;
        paymentService.cancelPayment(orderId);
        return ApiResponseUtil.success(ordersService.cancelOrder(userId, orderId));
    }
}
