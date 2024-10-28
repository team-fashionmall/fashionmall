package com.fashionmall.user.service;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.DeliveryAddressResponseDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.dto.response.UserInfoResponseDto;

import java.util.List;

public interface UserService {

    // User
    Long signUp (SignUpRequestDto signUpRequestDto);

    // DeliveryAddress
    Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId);

    List<DeliveryAddressResponseDto> getDeliveryAddress (Long userId);

    // Favorite
    FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId);

    PageInfoResponseDto<LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId);
}
