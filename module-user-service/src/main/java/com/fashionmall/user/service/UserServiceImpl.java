package com.fashionmall.user.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.user.dto.response.UserInfoResponseDto;
import com.fashionmall.user.jwt.JwtUtil;
import com.fashionmall.user.jwt.UserRoleEnum;
import com.fashionmall.common.redis.RedisUtil;
import com.fashionmall.user.dto.request.SignupRequestDto;
import com.fashionmall.user.dto.request.UpdateUserInfoRequestDto;
import com.fashionmall.user.entity.User;
import com.fashionmall.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Slf4j (topic = "userService")
@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public String signUp (SignupRequestDto signupRequestDto) {

        String email = signupRequestDto.getEmail();
        String nickname = signupRequestDto.getNickname();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        log.info("Received SignupRequestDto: {}", signupRequestDto.isAdmin());
        validateUser(email,nickname);

        // 사용자 ROLE 확인
        UserRoleEnum role = signupRequestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;

        // 사용자 회원가입 등록
        User user = User.builder()
                .email(email)
                .password(password)
                .username(signupRequestDto.getUsername())
                .nickname(nickname)
                .contact(signupRequestDto.getContact())
                .role(role)
                .build();

        userRepository.save(user);

        return "회원가입에 성공했습니다";
    }

    @Override
    @Transactional
    public String getRefreshToken (String refreshToken) {

        Claims info = jwtUtil.getUserInfoFromToken(refreshToken);

        Long userId = Long.valueOf(info.getId());

        // Redis에서 RefreshToken 문자열 가져오기
        String redisTokenStr = (String) redisUtil.get("refreshToken:" + userId);
        if (redisTokenStr == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Refresh token not found in Redis");
        }

        User user = userRepository.findById(Long.valueOf(info.getId()))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return jwtUtil.createToken(user.getEmail(), user.getRole(), user.getId(), jwtUtil.ACCESS_TOKEN_EXPIRATION_TIME);
    }

    @Override
    @Transactional
    public String updateUserInfo (UpdateUserInfoRequestDto updateUserInfoRequestDto, String username) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.UNAUTHORIZED_MEMBER));

        if (updateUserInfoRequestDto.getPassword() != null) {
            user.updatePassword(updateUserInfoRequestDto.getPassword());
        }
        if (updateUserInfoRequestDto.getNickname() != null) {
            user.updateNickname(updateUserInfoRequestDto.getNickname());
        }
        if (updateUserInfoRequestDto.getContact() != null) {
            user.updateContact(updateUserInfoRequestDto.getContact());
        }

        return "회원정보가 수정되었습니다";
    }

    @Override
    @Transactional
    public UserInfoResponseDto userInfo (String username) {

        // 본인 인증
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new CustomException(ErrorResponseCode.UNAUTHORIZED_MEMBER));

        // role이 admin일 때만 role 필드에 값 설정
        UserRoleEnum role = user.getRole() == UserRoleEnum.ADMIN ? user.getRole() : null;

        // 정보 조회 및 반환
        return UserInfoResponseDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .contact(user.getContact())
                .role(role)
                .build();
    }

    @Override
    @Transactional
    public Void logout (String accessToken) {

        // accessToken 검증 : 로그아웃 됐는지
        if (!jwtUtil.validateToken(accessToken)) {
            throw new CustomException(ErrorResponseCode.UNAUTHORIZED_MEMBER);
        }

        Claims info = jwtUtil.getUserInfoFromToken(accessToken);

        if (info.getId() == null) {
            throw new CustomException(ErrorResponseCode.UNAUTHORIZED_MEMBER);
        }

        // 레디스에 저장된 refreshtoken 제거
        Long userId = Long.valueOf(info.getId());

        // 모든 refreshToken 삭제
        Set<String> keys = redisUtil.getKeys("refreshToken:" + userId);
        for (String key : keys) {
            redisUtil.delete(key);
            log.info("Deleted refresh token from Redis: {}", key);
        }

        // 레디스 blacklist에 accesstoken 등록
        Long expiration = info.getExpiration().getTime() / 1000;
        redisUtil.setBlackList(accessToken, expiration);

        return null;
    }

    private void validateUser (String email, String nickname) {

        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_EMAIL); // 이미 존재하는 이메일입니다.
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_NICKNAME); // 이미 존재하는 닉네임 입니다.
        }
    }
}
