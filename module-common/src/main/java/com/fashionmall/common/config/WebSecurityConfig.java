package com.fashionmall.common.config;

//import com.fashionmall.common.jwt.CustomAuthenticationProvider;
//import com.fashionmall.common.jwt.JwtAuthenticationFilter;
import com.fashionmall.common.jwt.CustomAuthenticationProvider;
import com.fashionmall.common.jwt.JwtAuthorizationFilter;
import com.fashionmall.common.jwt.JwtUtil;
import com.fashionmall.common.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "webSecurityConfig")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    //    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationProvider customAuthenticationProvider;


//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
//
//        // CustomAuthenticationProvider를 AuthenticationManager에 추가
//        ((ProviderManager) authenticationManager).getProviders().add(customAuthenticationProvider);
//
//        return authenticationManager;
//    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();

    }

//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, redisUtil);
////        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
//        filter.setAuthenticationManager(authenticationManager()); // 커스텀 AuthenticationManager 사용
//        return filter;
//
//    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {

        return new JwtAuthorizationFilter(jwtUtil, redisUtil);

    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {

        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // session 아닌 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/user/signUp").permitAll()
                .requestMatchers("/user/login").permitAll()
//                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/user/auth/refresh").permitAll()
                .anyRequest().authenticated()
        );

//        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
