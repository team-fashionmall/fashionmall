//package com.fashionmall.common.jwt;
//
//
//import com.fashionmall.common.redis.RedisUtil;
//import com.fashionmall.common.redis.RefreshToken;
//import com.fashionmall.common.response.CommonResponse;
//import com.fashionmall.common.security.UserDetailsImpl;
//import com.fashionmall.common.util.ApiResponseUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.io.IOException;
//
//@Slf4j(topic = "로그인 및 JWT 생성")
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final JwtUtil jwtUtil;
//    private final RedisUtil redisUtil;
//
//    public JwtAuthenticationFilter (JwtUtil jwtUtil, RedisUtil redisUtil) {
//        this.jwtUtil = jwtUtil;
//        this.redisUtil = redisUtil;
//        setFilterProcessesUrl("/user/login");
//        super.setUsernameParameter("email");
//    }
//
//    @Override
//    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);
//
//            log.info("Attempting authentication for email: {}", loginRequestDto.getEmail());
//            log.info("Before authenticate call");
//
//
//            Authentication authentication = getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequestDto.getEmail(),
//                            loginRequestDto.getPassword(),
//                            null
//                    )
//            );
//
//            log.info("After authenticate call");
//            return authentication;  // 인증 결과 반환
//
////            return getAuthenticationManager().authenticate(
////                    new UsernamePasswordAuthenticationToken(
////                            loginRequestDto.getEmail(),
////                            loginRequestDto.getPassword(),
////                            null
////                    )
////            );
//        } catch (IOException e){
//            log.error("Authentication attempt failed: {}", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws  IOException, ServletException { // authResult & 오류 처리 확인
//
//        String email = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
//        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getRole();
//        Long userId = (((UserDetailsImpl) authResult.getPrincipal()).getUserid());
//
//        String accessToken = jwtUtil.createToken(email, role,userId, jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
//        String refreshToken = jwtUtil.createToken(email, role, userId, jwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);
//        System.out.println("Generated refresh token: " + refreshToken);
//
//        jwtUtil.createTokenToCookie("access_token", accessToken, jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
//        jwtUtil.createTokenToCookie("refresh_token", refreshToken, jwtUtil.REFRESH_TOKEN_EXPIRATION_TIME);
//        RefreshToken redisToken = new RefreshToken(userId, refreshToken);
//        redisUtil.set("refreshToken:" + userId, String.valueOf(redisToken), 86400);
//        System.out.println("Stored in Redis: refreshToken:" + userId + " = " + redisToken);
//
//        jwtUtil.addCookiesToResponse(response, accessToken, refreshToken);
//        // 추가 응답 처리
//        response.setStatus(HttpServletResponse.SC_OK);
//
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        log.error("Authentication failed: {}", failed.getMessage());
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    }
//
////    @Override
////    protected void unsuccessfulAuthentication (HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException { // 오류 났을때 다시 확인하기
////        response.setStatus(401);
////    }
//
//}