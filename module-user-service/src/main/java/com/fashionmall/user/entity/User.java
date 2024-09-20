package com.fashionmall.user.entity;

import com.fashionmall.user.jwt.UserRoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
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

    @Builder.Default
    @Enumerated (EnumType.STRING)
    @Column (nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'USER'") // 기본설정 되는지 확인하기
    private UserRoleEnum role = UserRoleEnum.USER;

}
