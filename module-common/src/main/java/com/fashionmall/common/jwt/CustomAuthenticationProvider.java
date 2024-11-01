package com.fashionmall.common.jwt;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (isValidUser(email, password)) {
            // 사용자 권한 설정 (예시로 ROLE_USER 부여)
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            return new UsernamePasswordAuthenticationToken(email, password, authorities);
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    private boolean isValidUser(String email, String password) {
        // 이메일과 비밀번호의 기본 유효성 검사
        return isValidEmail(email) && isValidPassword(password);
    }

    private boolean isValidEmail(String email) {
        // 이메일 형식 검증 (간단한 정규식 예시)
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPassword(String password) {
        // 비밀번호 복잡도 검증 (예: 최소 8자, 대문자, 소문자, 숫자, 특수문자 포함)
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordRegex, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

