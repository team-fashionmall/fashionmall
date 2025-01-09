package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.response.FavoriteResponseDto;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "favorite_controller")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final UserService userService;

    @PostMapping("/favorite/{itemId}")
    public CommonResponse<FavoriteResponseDto> createFavorite(@PathVariable Long itemId,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.createFavorite(itemId, userDetails.getUserid()));
    }

    @DeleteMapping("/favorite/{itemId}")
    public void deleteFavorite(@PathVariable Long itemId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.deleteFavorite(itemId, userDetails.getUserid());
    }
}
