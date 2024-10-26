package com.fashionmall.order.service;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.request.BillingKeyRequestDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;

public interface BillingKeyService {

    Long issueBillingKey(BillingKeyRequestDto billingKeyRequestDto);

    PageInfoResponseDto<UserBillingKeyResponseDto> getUserBillingKeyList(Long userId, int pageNo, int size);

    Long deleteBillingKey(Long billingKeyId);
}
