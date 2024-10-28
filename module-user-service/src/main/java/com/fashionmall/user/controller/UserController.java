package com.fashionmall.user.controller;

import com.fashionmall.common.response.CommonResponse;
import com.fashionmall.common.util.ApiResponseUtil;
import com.fashionmall.user.dto.request.SignUpRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.service.UserService;
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

    @PostMapping("/user/signUp")
    public CommonResponse<Long> signUp (@Valid @RequestBody SignUpRequestDto signUpRequestDto) {

        return ApiResponseUtil.success(userService.signUp(signUpRequestDto));
    }
}
