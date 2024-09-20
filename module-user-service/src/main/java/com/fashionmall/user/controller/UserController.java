package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.response.PageInfoResponseDto;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.SignupRequestDto;
import com.fashionmall.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j (topic = "user controller")
@RestController
@RequiredArgsConstructor
@RequestMapping ("/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping ("/signup")
    public CommonResponse<Long> signUp (@Valid @RequestBody SignupRequestDto signupRequestDto) {

        return ApiResponseUtil.success(userService.signUp(signupRequestDto)); // 결과값 어떻게 나오는지 확인하기

    }

}
