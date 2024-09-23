package com.fashionmall.user.security;

import com.fashionmall.user.jwt.UserRoleEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j (topic = "UserDetailsImpl")
@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final UserRoleEnum role;
    private final Long userId;
    private final String password;

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername(){
        return email;
    }

    public Long getUserid(){
        return userId;
    }

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {

        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
