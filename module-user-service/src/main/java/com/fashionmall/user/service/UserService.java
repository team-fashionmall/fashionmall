package com.fashionmall.user.service;

import com.fashionmall.user.dto.request.SignupRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.dto.response.UserInfoResponseDto;


public interface UserService {

    String signUp (SignupRequestDto signupRequestDto);

    String getRefreshToken (String refreshToken);

    String updateUserInfo (UpdateUserInfoRequestDto updateUserInfoRequestDto, String username);

    UserInfoResponseDto userInfo(String username);

    Void logout(String accessToken);

}
