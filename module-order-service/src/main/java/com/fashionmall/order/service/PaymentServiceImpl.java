package com.fashionmall.order.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.order.dto.request.PaymentRequestDto;
import com.fashionmall.order.dto.response.PaymentResponseDto;
import com.fashionmall.order.entity.Orders;
import com.fashionmall.order.entity.Payment;
import com.fashionmall.order.enums.PaymentStatus;
import com.fashionmall.order.infra.iamPort.dto.IamPortResponseDto;
import com.fashionmall.order.infra.iamPort.util.IamPortClient;
import com.fashionmall.order.repository.OrdersRepository;
import com.fashionmall.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrdersRepository ordersRepository;

    private final IamPortClient iamPortClient;

    @Transactional
    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {

        Long orderId = paymentRequestDto.getOrderId();

        String merchantUid = createMerchantUid(orderId);
        paymentRequestDto.setMerchantUid(merchantUid);

        //비인증 일회성 결제
        IamPortResponseDto<PaymentResponseDto> post = iamPortClient.onetimePayment(paymentRequestDto);

        Orders orders = ordersRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

        String impUid = post.getResponse().getImpUid();
        long unixPaidAt = (long) post.getResponse().getPaidAt();
        LocalDateTime paidAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixPaidAt), ZoneId.systemDefault());

        Payment payment = paymentRequestDto.toPayment(orders, impUid, paidAt);

        paymentRepository.save(payment);


        return post.getResponse();
    }

    @Override
    public PaymentResponseDto cancelPayment(Long orderId) {

        Payment payment = paymentRepository.findByOrdersId(orderId)
                .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setMerchantUid(payment.getMerchantUid());
        paymentRequestDto.setCancelReason(paymentRequestDto.getCancelReason());

        IamPortResponseDto<PaymentResponseDto> post = iamPortClient.cancelPayment(paymentRequestDto);

        int cancelAmount = post.getResponse().getCancelAmount();
        long unixCancelledAt = post.getResponse().getCancelledAt();
        LocalDateTime cancelledAt = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixCancelledAt), ZoneId.systemDefault());

        payment.setCancelPrice(cancelAmount);
        payment.setCanceled_at(cancelledAt);
        payment.setCancelReason(paymentRequestDto.getCancelReason());
        payment.setStatus(PaymentStatus.CANCELED);

        return post.getResponse();
    }

    public String createMerchantUid(Long orderId) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String counterNumber = String.format("%03d", orderId);
        return "FM-" + date + "-" + counterNumber;
    }
}
