package com.fashionmall.common.jwt;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    // Access Token 쿠키에 넣어 생성
    public ResponseCookie createAccessTokenCookie (String accessToken) { // accessToken 들어오는지 확인

        return ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(ACCESS_TOKEN_EXPIRATION_TIME / 1000)
                .sameSite("Strict")
                .build();
    }

    // Refresh Token 쿠키에 넣어 생성
    public ResponseCookie createRefreshTokenCookie (String refreshToken) { // refreshToken 들어오는지 확인

        return ResponseCookie.from("access_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRATION_TIME / 1000)
                .sameSite("Strict")
                .build();
    }

    // 쿠키 Response 하기
    public void addCookiesToResponse (HttpServletResponse response, String accessToken, String refreshToken) { // token 들어오는지 확인

        ResponseCookie accessCookie = createAccessTokenCookie(accessToken);
        ResponseCookie refreshCookie = createRefreshTokenCookie(refreshToken);

        response.addHeader("Set-AccessCookie", accessCookie.toString());
        response.addHeader("Set-RefreshCookie", refreshCookie.toString());
    }

    // JWT Token substring
    public String substringToken (String tokenValue) { // // tokenValue 들어오는지 확인
        if (StringUtils.hasText (tokenValue) && tokenValue.startsWith (BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new CustomException(ErrorResponseCode.JWT_NOT_FOUND_TOKEN);
    }

    // JWT Token 검증
    public boolean validateToken (String token) { // token 들어오는지 확인
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

}
