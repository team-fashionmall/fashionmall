package com.fashionmall.user.dto.response;

import com.fashionmall.user.entity.User;
import com.fashionmall.common.jwt.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private String email;
    private String password;
    private String username;
    private String nickname;
    private String contact;
    private UserRoleEnum role;

    public static UserInfoResponseDto from (User user) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getNickname(),
                user.getContact(),
                user.getRole()
        );
    }
}
