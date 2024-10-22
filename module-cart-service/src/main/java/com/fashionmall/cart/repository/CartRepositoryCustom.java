package com.fashionmall.cart.repository;

import com.fashionmall.cart.dto.response.CartResponseDto;
import com.fashionmall.common.moduleApi.dto.ItemDetailDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartRepositoryCustom {

    List<ItemDetailDto> getItemDetailFromCartApi(Long userId);

    PageInfoResponseDto<CartResponseDto> getCartList (Pageable pageable, Long userId, String imageUrl);
}
