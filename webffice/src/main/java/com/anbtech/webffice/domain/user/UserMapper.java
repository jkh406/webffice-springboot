package com.anbtech.webffice.domain.user;

import java.util.List;
import java.util.Optional;

import com.anbtech.webffice.domain.user.dto.UserDTO;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void join(UserDTO userVo);  
    Optional<UserDTO> findUser(String userId);
    Optional<UserDTO> findUserId(String userId);
}
