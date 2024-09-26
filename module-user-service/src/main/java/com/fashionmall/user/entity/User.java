package com.fashionmall.user.entity;

import com.fashionmall.user.jwt.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor (access = AccessLevel.PROTECTED)
@Table (name = "users")
public class User {

    @Id @Column (name = "user_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false, unique = true)
    private String nickname;

    @Column (nullable = false)
    private String contact;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false) // 기본설정 되는지 확인하기
    private UserRoleEnum role;

    @Builder
    public User (String email, String password, String username, String nickname, String contact, UserRoleEnum role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.nickname = nickname;
        this.contact = contact;
        this.role = role;
    }

    public void updatePassword (String password) {
        this.password = password;
    }

    public void updateNickname (String nickname) {
        this.nickname = nickname;
    }

    public void updateContact (String contact) {
        this.contact = contact;
    }

}
