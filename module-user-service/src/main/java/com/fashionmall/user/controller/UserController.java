package com.fashionmall.user.controller;

import com.fashionmall.common.jwt.JwtUtil;
import com.fashionmall.common.jwt.LoginRequestDto;
import com.fashionmall.common.redis.RefreshToken;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.LoginResponseDto;
import com.fashionmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/user/signUp")
    public CommonResponse<Long> signUp (@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        return ApiResponseUtil.success(userService.signUp(signUpRequestDto));
    }

    @PostMapping("/user/login")
    public CommonResponse<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        return ApiResponseUtil.success(userService.login(loginRequestDto, request, response));
    }

    @PatchMapping("/user/{userId}")
    public CommonResponse<Long> updateUserInfo (@Valid @RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto) {
        Long userId = 1L;
        return ApiResponseUtil.success(userService.updateUserInfo(updateUserInfoRequestDto, userId));
    }

    @PostMapping ("/auth/refresh")
    public CommonResponse<String> getRefreshToken (@RequestBody RefreshToken refreshToken) {
        return ApiResponseUtil.success(userService.getRefreshToken (refreshToken.getRefreshToken()));
    }

}
