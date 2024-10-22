package com.fashionmall.cart.repository;

import com.fashionmall.common.moduleApi.dto.ItemDetailDto;

import java.util.List;

public interface CartRepositoryCustom {

    List<ItemDetailDto> getItemDetailFromCartApi(Long userId);
}
