package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.FavoriteRequestDto;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "favorite_controller")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PatchMapping ("/favorite/{itemId}")
    public CommonResponse <FavoriteResponseDto> updateFavorite (@PathVariable Long itemId, @Valid @RequestBody FavoriteRequestDto favoriteRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(favoriteService.updateFavorite(itemId, favoriteRequestDto , userId));
    }
}
