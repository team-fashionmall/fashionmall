package com.fashionmall.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j (topic = "JWT Util")
@Component
public class JwtUtil {

    // 쿠키의 Header Name 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // 사용자 권한 Key 값
    public static final String AUTHORIZATION_ADMIN_KEY = "auth";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";

    // 토큰 만료시간
    private final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 60분
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 24시간

    // Secret Key
    @Value("${jwt.secret.key")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // Access Token 생성
    public String createAccessToken (String email, UserRoleEnum role) {

        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(email) // 사용자 식별자값
                .claim(AUTHORIZATION_ADMIN_KEY, role) // 사용자 권한
                .setIssuedAt(date) // 발급일
                .setExpiration(new Date (date.getTime() + ACCESS_TOKEN_EXPIRATION_TIME)) // Access Token 만료시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    // Refresh Token 생성
    public String createRefreshToken (String email, UserRoleEnum role) {

        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(email) // 사용자 식별자값
                .claim(AUTHORIZATION_ADMIN_KEY, role) // 사용자 권한
                .setIssuedAt(date) // 발급일
                .setExpiration(new Date (date.getTime() + REFRESH_TOKEN_EXPIRATION_TIME)) // Refresh Token 만료시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact();
    }

    // JWT Token substring
    //public String substringToken (String )

}
