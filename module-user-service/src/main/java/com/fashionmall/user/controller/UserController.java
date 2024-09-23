package com.fashionmall.user.controller;

import com.fashionmall.user.jwt.JwtUtil;
import com.fashionmall.common.redis.RefreshToken;
import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.user.security.UserDetailsImpl;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.SignupRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j (topic = "user controller")
@RestController
@RequiredArgsConstructor
@RequestMapping ("/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping ("/signup")
    public CommonResponse<String> signUp (@Valid @RequestBody SignupRequestDto signupRequestDto) {

        return ApiResponseUtil.success(userService.signUp(signupRequestDto));

    }

    @PostMapping ("/auth/refresh")
    public CommonResponse<String> getRefreshToken (@RequestBody RefreshToken refreshToken) {
        return ApiResponseUtil.success(userService.getRefreshToken (refreshToken.getRefreshToken()));
    }

    @PatchMapping ("/{userId}")
    public CommonResponse<String> updateUserInfo (@Valid @RequestBody UpdateUserInfoRequestDto updateUserInfoRequestDto,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ApiResponseUtil.success(userService.updateUserInfo(updateUserInfoRequestDto, userDetails.getUsername()));
    }

    @PostMapping ("/logout")
    public CommonResponse<Void> logout (HttpServletRequest request) {

        String accessToken = jwtUtil.getJwtFromHeader(request);

        return ApiResponseUtil.success(userService.logout(accessToken));
    }

}
