package com.anbtech.webffice.domain.user;

import java.util.List;

import lombok.Data;

@Data
public class UserVo {
    private String userId;
    private String userPw;
    private List<String> pageList;
}