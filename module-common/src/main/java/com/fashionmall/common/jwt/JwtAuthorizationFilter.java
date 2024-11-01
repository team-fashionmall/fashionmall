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

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if ("/user/signUp".equals(path) || "/user/login".equals(path) || "/user/auth/refresh".equals(path)) { filterChain.doFilter(request, response); return;}

        if (request.getCookies() != null && request.getCookies().length > 0) {
            log.info("request {} {}", request.getCookies()[0].getName(), request.getCookies()[0].getValue());
        }

        String tokenValue = jwtUtil.getTokenFromRequest(request, HttpHeaders.AUTHORIZATION);
        System.out.println("tokenValue = " + tokenValue);

        if (StringUtils.hasText(tokenValue)) {

            tokenValue = jwtUtil.substringToken(tokenValue);

            if (redisUtil.hasKeyBlackList(tokenValue)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            if (!jwtUtil.validateToken(tokenValue)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

            try {
                setAuthentication(info.getSubject(), info.getId(), info.get(AUTHORIZATION_ROLE_KEY, UserRoleEnum.class));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

        filterChain.doFilter(request, response);

    }

    public void setAuthentication(String email, String userId, UserRoleEnum role) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication (email, userId, role);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

    }

    private Authentication createAuthentication (String email, String userId, UserRoleEnum role) {

        UserDetails userDetails = new UserDetailsImpl(email, Long.valueOf(userId), role);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
