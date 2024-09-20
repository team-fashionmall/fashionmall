package com.fashionmall.user.service;

import com.fashionmall.user.dto.request.SignupRequestDto;

public interface UserService {

    Long signUp (SignupRequestDto signupRequestDto);

}
