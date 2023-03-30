package com.anbtech.webffice.domain.user.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.anbtech.webffice.domain.user.UserController;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class UserDTO implements UserDetails {
    private String userId;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPw;
    private String userRole;
    // private String userAuth;
    private String createDate;
    private String updateDate;


    // 이하 코드는 security 를 위한 용도
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public String getUsername() {
    	log.info("username !!!" + username);
        return this.username;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}