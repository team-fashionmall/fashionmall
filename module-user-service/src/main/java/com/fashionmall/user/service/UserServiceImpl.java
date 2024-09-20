package com.fashionmall.user.service;

import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import com.fashionmall.user.jwt.UserRoleEnum;
import com.fashionmall.user.dto.request.SignupRequestDto;
import com.fashionmall.user.entity.User;
import com.fashionmall.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional (readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional // 위에 했는데도 필요한가?
    public Long signUp (SignupRequestDto signupRequestDto) {

        String email = signupRequestDto.getEmail();
        String nickname = signupRequestDto.getNickname();
        String password = passwordEncoder.encode(signupRequestDto.getPassword()); // 비밀번호 암호화 하기

        // 회원 중복 확인 (email)
        Optional <User> checkEmail = userRepository.findByEmail(email);

        if (checkEmail.isPresent()) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_EMAIL); // 이미 존재하는 이메일입니다.
        }

        // 닉네임 중복 확인
        Optional <User> checkNickname = userRepository.findByNickname(nickname);

        if (checkNickname.isPresent()) {
            throw new CustomException(ErrorResponseCode.DUPLICATE_NICKNAME); // 이미 존재하는 닉네임 입니다.
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;

        if (signupRequestDto.isAdmin()) {
            role = UserRoleEnum.ADMIN;
        }

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

        return user.getId(); // 리턴값으로 뭘 줘야하는거지?
    }
}
