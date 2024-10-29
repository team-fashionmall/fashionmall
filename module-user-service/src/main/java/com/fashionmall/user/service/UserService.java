package com.fashionmall.user.service;

import com.fashionmall.common.moduleApi.dto.DeliveryAddressDto;
import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.DeliveryAddressRequestDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.dto.response.UserInfoResponseDto;

import java.util.List;

public interface UserService {

    // User
    Long signUp (SignUpRequestDto signUpRequestDto);

    Long updateUserInfo (UpdateUserInfoRequestDto updateUserInfoRequestDto, Long userId);

    UserInfoResponseDto userInfo(Long userId);

    // DeliveryAddress
    Long createDeliveryAddress (DeliveryAddressRequestDto deliveryAddressRequestDto, Long userId);

    List<DeliveryAddressDto> getDeliveryAddress (Long userId);

    // Favorite
    FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId);

    PageInfoResponseDto<LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId);
}
