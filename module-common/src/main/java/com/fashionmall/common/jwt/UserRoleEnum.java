package com.fashionmall.common.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRoleEnum {

    USER (Authority.USER),
    ADMIN (Authority.ADMIN);

    private final String authority;

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

}
