package com.fashionmall.order.service;

import com.fashionmall.order.dto.request.PaymentRequestDto;
import com.fashionmall.order.dto.response.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);

    PaymentResponseDto cancelPayment(Long orderId);

    PaymentResponseDto testPayment();
}