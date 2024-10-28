package com.fashionmall.user.service;

import com.fashionmall.common.moduleApi.dto.LikeItemListResponseDto;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import org.springframework.data.domain.Page;

public interface FavoriteService {

    FavoriteResponseDto updateFavorite (Long itemId, FavoriteRequestDto favoriteRequestDto, Long userId);

    PageInfoResponseDto<LikeItemListResponseDto> favoriteList (int pageNo, int size, Long itemId, Long userId);
}
