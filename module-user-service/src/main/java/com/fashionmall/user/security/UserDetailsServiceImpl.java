package com.fashionmall.user.security;


import com.fashionmall.user.entity.User;
import com.fashionmall.user.jwt.JwtUtil;
import com.fashionmall.user.jwt.UserRoleEnum;
import com.fashionmall.user.repository.UserRepository;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.fashionmall.user.jwt.JwtUtil.AUTHORIZATION_ADMIN_KEY;

@Slf4j (topic = "UserDetailsServiceImpl")
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        // JWT 형식 확인 (토큰에 두 개의 점이 있는지 확인)
        if (token == null || token.split("\\.").length != 3) {
            throw new MalformedJwtException("Invalid JWT token format");
        }

        // JWT 토큰에서 클레임 정보 추출
        Claims info = jwtUtil.getUserInfoFromToken(token);

        // 이메일(subject), role, userId 가져오기
        String email = info.getSubject();
        UserRoleEnum role = UserRoleEnum.valueOf(info.get(AUTHORIZATION_ADMIN_KEY, String.class));
        Long userId = Long.valueOf(info.getId());

        // UserDetailsImpl 객체 생성 후 반환
        return new UserDetailsImpl(email, role, userId);
    }

}
