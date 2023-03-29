package com.anbtech.webffice.global.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.global.DTO.response.SingleDataResponse;
import com.anbtech.webffice.global.jwt.JwtTokenProvider;
import com.anbtech.webffice.global.service.ResponseService;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
public class TokenController {
    
    @Autowired
    ResponseService responseService;
    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(value = "/getAccessToken")
    public ResponseEntity issueToken(
        // @RequestHeader(value = "Authorization") String accessTokenRequest,
        @CookieValue(value = HttpHeaders.SET_COOKIE) Cookie refreshCookie
     ) {
        ResponseEntity responseEntity = null;
        try{
            String refreshToken = tokenProvider.resolveToken(refreshCookie.getValue());

            // 유저 권한 저장 들어있는 컬렉션
            Collection<? extends GrantedAuthority> accessTokenAuthoriryCollection = tokenProvider.getAuthentication(refreshToken).getAuthorities();
            
            // 유저 권한 저장 위한 리스트
            List<String> accessTokenAuthorities = new ArrayList<String>(accessTokenAuthoriryCollection.size());

            String accessTokenUserId = tokenProvider.getUserId(refreshToken);
            String newAccessToken = null;
            
            // 리프레시 토큰이 유효하다면
            if (StringUtils.hasText(refreshToken) && tokenProvider.validateToken(refreshToken)) {
                for (GrantedAuthority x : accessTokenAuthoriryCollection) {
                    accessTokenAuthorities.add(x.getAuthority());
                }
                newAccessToken = "Bearer" + tokenProvider.createAcessToken(accessTokenUserId, accessTokenAuthorities);
                log.debug("토큰 재발급 성공");
                SingleDataResponse<String> response = responseService.getSingleDataResponse(true, "accessToken 발급성공", newAccessToken);
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
    
            }else {
                SingleDataResponse<String> response = responseService.getSingleDataResponse(false, "accessToken 발급 실패, refreshToken 유효기간 만료", "Tk402");
                responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
        return responseEntity;
        }catch (Exception e) {
            return responseEntity;
        }
    }
}