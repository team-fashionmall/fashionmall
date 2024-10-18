package com.fashionmall.order.repository;

import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.order.dto.response.UserBillingKeyResponseDto;
import org.springframework.data.domain.Pageable;

public interface BillingKeyRepositoryCustom {

    PageInfoResponseDto<UserBillingKeyResponseDto> findBillingKeyByUserId(Long userId, Pageable pageable);

    String findCustomerUidById(Long id);
}
