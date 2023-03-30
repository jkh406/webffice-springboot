package com.anbtech.webffice.domain.user;

import java.util.Optional;

import com.anbtech.webffice.domain.user.dto.UserDTO;

import org.apache.ibatis.annotations.Mapper;

// @mapper
@Mapper
public interface UserMapper {
    void join(UserDTO userVo);
    Optional<UserDTO> findUser(String userId);
    Optional<UserDTO> findUserId(String userId);

    // 로그인
    UserVo getUserAccount(String userId);

    // 회원가입
    void saveUser(UserVo userVo);
}
