package com.fashionmall.common.jwt;


import com.fashionmall.common.redis.RedisUtil;
import com.fashionmall.common.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.fashionmall.common.jwt.JwtUtil.AUTHORIZATION_ROLE_KEY;

@Slf4j(topic = "JWT 검증 및 인가(권한 확인?!)")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    //    private final UserDetailsServiceImpl userDetailsService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if ("/user/signUp".equals(path) || "/user/login".equals(path) || "/user/auth/refresh".equals(path)) { filterChain.doFilter(request, response); return;}

        // 요청에 대한 로그
        if (request.getCookies() != null && request.getCookies().length > 0) {
            log.info("request {} {}", request.getCookies()[0].getName(), request.getCookies()[0].getValue());
        }

        log.info ("request {} {} ", request.getCookies()[0].getName(), request.getCookies()[0].getValue());

        // JWT 토큰 추출
        String tokenValue = jwtUtil.getTokenFromRequest(request, HttpHeaders.AUTHORIZATION); //--------------
        System.out.println("tokenValue = " + tokenValue);

        // 토큰이 존재하는지 확인
        if (StringUtils.hasText(tokenValue)) {

            tokenValue = jwtUtil.substringToken(tokenValue);

            if (redisUtil.hasKeyBlackList(tokenValue)) {
                log.warn("블랙리스트 검증에 걸림: {}", tokenValue);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if (!jwtUtil.validateToken(tokenValue)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject(), info.getId(), info.get(AUTHORIZATION_ROLE_KEY, UserRoleEnum.class));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    // 인증 처리
    public void setAuthentication(String email, String userId, UserRoleEnum role) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication (email, userId, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

    }

    // 인증 객체 생성
    private Authentication createAuthentication (String email, String userId, UserRoleEnum role) {

        UserDetails userDetails = new UserDetailsImpl(email, Long.valueOf(userId), role);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
