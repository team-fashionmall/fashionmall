package com.fashionmall.order.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.order.dto.request.PaymentRequestDto;
import com.fashionmall.order.dto.response.PaymentResponseDto;
import com.fashionmall.order.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/order/{ordersId}/payment")
    public CommonResponse<PaymentResponseDto> processPayment(@PathVariable("ordersId") Long ordersId, PaymentRequestDto paymentRequestDto) {
        paymentRequestDto.setOrderId(ordersId);
        return ApiResponseUtil.success(paymentService.createPayment(paymentRequestDto));
    }
}