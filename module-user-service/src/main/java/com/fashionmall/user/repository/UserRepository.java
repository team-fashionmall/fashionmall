package com.fashionmall.user.repository;

import com.fashionmall.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Optional<User> findByUserName(String userName);
}
