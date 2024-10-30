//package com.fashionmall.common.security;
//
//import com.fashionmall.common.exception.CustomException;
//import com.fashionmall.common.exception.ErrorResponseCode;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Slf4j (topic = "UserDetailsServiceImpl")
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername (String email, String userId) throws UsernameNotFoundException {
//
//        return new UserDetailsImpl(email, user.getRole(), userId, user.getPassword());
//    }
//}
