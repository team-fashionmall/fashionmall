package com.fashionmall.user.repository;

import com.fashionmall.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Optional <User> findByEmail (String email);
    boolean existsByEmail(String email);

    Optional <User> findByNickname (String nickname);
    boolean existsByNickname(String nickname);
}
