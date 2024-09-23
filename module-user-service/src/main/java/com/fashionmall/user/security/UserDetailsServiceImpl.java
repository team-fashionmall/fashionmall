package com.fashionmall.user.security;


import com.fashionmall.user.entity.User;
import com.fashionmall.user.repository.UserRepository;
import com.fashionmall.common.exception.CustomException;
import com.fashionmall.common.exception.ErrorResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j (topic = "UserDetailsServiceImpl")
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(()-> new CustomException(ErrorResponseCode.DUPLICATE_EMAIL));

        return new UserDetailsImpl(email, user.getRole(), user.getId(), user.getPassword());
    }
}
