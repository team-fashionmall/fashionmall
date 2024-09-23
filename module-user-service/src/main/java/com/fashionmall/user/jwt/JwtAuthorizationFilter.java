package com.fashionmall.user.jwt;

import com.fashionmall.common.redis.RedisUtil;
import com.fashionmall.user.security.UserDetailsServiceImpl;
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

@Slf4j (topic = "JWT 검증 및 인가(권한 확인?!)")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if ("/user/signup".equals(path) || "/user/login".equals(path) || "/user/auth/refresh".equals(path)) { filterChain.doFilter(request, response); return;}

        log.info ("request {} {} ", request.getCookies()[0].getName(), request.getCookies()[0].getValue());
        String tokenValue = jwtUtil.getTokenFromRequest(request, HttpHeaders.AUTHORIZATION); //--------------
        System.out.println("tokenValue = " + tokenValue);

        if (StringUtils.hasText(tokenValue)) {

            tokenValue = jwtUtil.substringToken(tokenValue);

            if (redisUtil.hasKeyBlackList(tokenValue)) {
                log.warn("블랙리스트 검증에 걸림: {}", tokenValue);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if (!jwtUtil.validateToken(tokenValue)) {
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    // 인증 처리
    public void setAuthentication(String email) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication (email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

    }

    // 인증 객체 생성
    private Authentication createAuthentication (String email) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
