package com.fashionmall.user.service;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;

public interface UserService {

    Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId);

    FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId);

    PageInfoResponseDto<LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId);
}
