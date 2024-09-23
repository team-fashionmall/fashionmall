package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.response.UserInfoResponseDto;
import com.fashionmall.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserQueryController {

    private final UserService userService;

    @GetMapping ("/user/info")
    public CommonResponse<UserInfoResponseDto> userInfo (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.userInfo(userDetails.getUsername()));
    }

}
