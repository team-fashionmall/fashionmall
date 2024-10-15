package com.fashionmall.order.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.common.moduleApi.dto.CouponDto;
import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.OrderItemRequestDto;
import com.fashionmall.order.dto.request.OrdersRequestDto;
import com.fashionmall.order.dto.response.OrderItemDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersCompleteResponseDto;
import com.fashionmall.order.dto.response.OrdersDetailResponseDto;
import com.fashionmall.order.dto.response.OrdersResponseDto;
import com.fashionmall.order.entity.Orders;
import com.fashionmall.order.entity.Payment;
import com.fashionmall.order.enums.OrderStatus;
import com.fashionmall.order.repository.OrdersRepository;
import com.fashionmall.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final ModuleApiUtil moduleApiUtil;
    private final OrdersRepository ordersRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Long createOrder(OrdersRequestDto ordersRequestDto) {
        Long userId = ordersRequestDto.getUserId();
        Long couponId = ordersRequestDto.getCouponId();
        Long deliveryAddressId = ordersRequestDto.getDeliveryAddressId();

        //API 데이터 가져오기
        List<CouponDto> userCouponApi = moduleApiUtil.getUserCouponApi(userId);
        List<DeliveryAddressDto> userDeliveryAddressApi = moduleApiUtil.getUserDeliveryAddressApi(userId);
        List<ItemDetailDto> itemDetailApi = moduleApiUtil.getItemDetailFromCartApi(userId);

        //주문 항목 생성
        List<OrderItemRequestDto> orderItems = itemDetailApi.stream()
                .map(itemDetail -> new OrderItemRequestDto(
                        itemDetail.getId(),
                        itemDetail.getName(),
                        itemDetail.getPrice(),
                        itemDetail.getQuantity()
                ))
                .toList();

        ordersRequestDto.setOrderItemsDto(orderItems);

        //재고 확인
        for (OrderItemRequestDto orderItem : orderItems) {
            Long itemDetailId = orderItem.getItemDetailId();
            int quantity = orderItem.getQuantity();

            int itemQuantityApi = moduleApiUtil.getItemQuantityApi(itemDetailId);

            if (quantity > itemQuantityApi) {
                throw new CustomException(ErrorResponseCode.OUT_OF_STOCK);
            }
        }

        //배송지 설정
        if (deliveryAddressId != null) {
            DeliveryAddressDto deliveryAddressDto = userDeliveryAddressApi.stream()
                    .filter(deliveryAddress -> deliveryAddressId.equals(deliveryAddress.getId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

            String zipcode = deliveryAddressDto.getZipcode();
            String roadAddress = deliveryAddressDto.getRoadAddress();

            ordersRequestDto.setZipcode(zipcode);
            ordersRequestDto.setRoadAddress(roadAddress);
        }

        //가격 계산
        int totalPrice = itemDetailApi.stream().mapToInt(ItemDetailDto::getPrice).sum();
        int discountPrice = 0;

        //쿠폰 적용
        if (couponId != null) {
            CouponDto couponDto = userCouponApi.stream()
                    .filter(coupon -> coupon.getId().equals(couponId))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));

            String discountType = couponDto.getDiscountType();
            int discountValue = couponDto.getDiscountValue();

            if (discountType.equals("RATE")) {
                discountPrice = (int) (totalPrice * (discountPrice / 100.0));
            } else {
                discountPrice = discountValue;
            }
        }

        int paymentPrice = totalPrice - discountPrice;

        //DTO 정보 입력
        ordersRequestDto.setTotalPrice(totalPrice);
        ordersRequestDto.setDiscountPrice(discountPrice);
        ordersRequestDto.setPaymentPrice(paymentPrice);

        Orders orders = ordersRequestDto.toOrders();

        return ordersRepository.save(orders).getId();
    }

    @Override
    public OrdersCompleteResponseDto completeOrder(Long orderId) {

        List<OrderItemDto> orderItemsByOrderId = ordersRepository.findOrderItemsByOrderId(orderId);
        //재고차감
        moduleApiUtil.deductItemQuantityApi(orderItemsByOrderId);

        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));
        Payment payment = paymentRepository.findByOrdersId(orderId).orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));
        orders.setPayment(payment);

        return ordersRepository.findByOrderId(orderId);
    }

    @Override
    public PageInfoResponseDto<OrdersResponseDto> getOrders(Long userId, int pageNo, int size) {
        PageRequest pageRequest = PageRequest.of(pageNo - 1, size);
        PageInfoResponseDto<OrdersResponseDto> ordersByUserId = ordersRepository.findOrdersByUserId(userId, pageRequest);
        //item image, name 가져오기(추가하기)

        return ordersByUserId;
    }

    @Override
    public OrdersDetailResponseDto getOrderDetail(Long userId, Long orderId) {
        OrdersDetailResponseDto ordersDetails = ordersRepository.findOrdersDetailsByUserIdAndOrderId(userId, orderId);

        List<Long> itemDetailIds = ordersDetails.getOrderItemsDto().stream()
                .map(OrderItemDetailResponseDto::getItemDetailId)
                .toList();

        Map<Long, String> itemDetailNames = moduleApiUtil.getItemDetailNameApi(itemDetailIds);

        ordersDetails.getOrderItemsDto()
                .forEach(orderItem -> orderItem.setItemDetailName(itemDetailNames.get(orderItem.getItemDetailId())));

        return ordersDetails;
    }

    @Override
    public Long cancelOrder(Long userId, Long orderId) {

        Long cancelOrder = ordersRepository.cancelOrderById(userId, orderId);

        if (cancelOrder != null) {
            List<OrderItemDto> orderItemsByOrderId = ordersRepository.findOrderItemsByOrderId(orderId);
            moduleApiUtil.restoreItemQuantityApi(orderItemsByOrderId);
        } else {
            throw new CustomException(ErrorResponseCode.NOT_FOUND);
        }

        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorResponseCode.NOT_FOUND));
        orders.setStatus(OrderStatus.CANCELED);

        return orderId;
    }


}