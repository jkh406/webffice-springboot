package com.anbtech.webffice.global.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import com.anbtech.webffice.domain.user.dto.UserDTO;
import com.anbtech.webffice.domain.user.service.UserService;

public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    UserService userService;
    
	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
		// 인증 정보 가져오기
		Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		AuthorizationDecision decision = new AuthorizationDecision(false);
		
		// 인증된 사용자가 없으면 접근 거부
		if(authenticate == null) {
			return decision = new AuthorizationDecision(false);
		}

		String strUser_ID = ((UserDTO) authenticate.getPrincipal()).getUser_ID();
		
		System.out.print("AuthorizationDecision use name = " + strUser_ID);
		
		// 인증된 사용자의 권한 정보 가져오기 
		List<UserDTO> userDtoList = userService.pageList(strUser_ID); // 사용자 이름으로 유저 정보를 디비에서 가져옴
		
		List<Map<String, Object>> pageAuthorityMap = new ArrayList<>();

		for(UserDTO userDto : userDtoList) {
		    Map<String, Object> resultMap = new HashMap<>();
		    resultMap.put("user_ID", userDto.getUser_ID());
		    resultMap.put("authority", userDto.getAuthority());
		    resultMap.put("page_url", userDto.getPage_url());
		    pageAuthorityMap.add(resultMap);
		}
		System.out.print("AuthorizationDecision user pageAuthorityMap " + pageAuthorityMap);
		
		// 요청한 URL의 접근 권한 가져오기
		String requestedUrl = object.getRequest().getRequestURI();
		String requestedMethod = object.getRequest().getMethod();
		System.out.print("AuthorizationDecision requestedUrl " + requestedUrl);
		System.out.print("AuthorizationDecision requestedMethod " + requestedMethod);
		
		
//		Set<String> urlRoles = getUrlRoles(requestedUrl, requestedMethod); // 요청한 URL에 필요한 권한 정보를 디비에서 가져옴
		
		// 유저의 권한과 URL의 접근 권한 비교하여 접근 허용 여부 결정
//		for(String role : urlRoles) {
//			if(userRoles.contains(role)) {
//				return decision = new AuthorizationDecision(true);
//			}
//		}
		
		// 권한이 없으면 접근 거부
		return decision = new AuthorizationDecision(false);
	}
	
	private Set<String> getUrlRoles(String requestedUrl, String requestedMethod) {
	    Set<String> urlRoles = new HashSet<>();
	    
	    // requestedUrl과 requestedMethod에 기반하여 디비에서 권한 정보를 가져와서 urlRoles에 추가하는 로직
	    if (requestedUrl.equals("/admin") && requestedMethod.equals("GET")) {
	        urlRoles.add("ADMIN_ROLE");
	    } else if (requestedUrl.equals("/user") && requestedMethod.equals("GET")) {
	        urlRoles.add("USER_ROLE");
	    }
	    
	    return urlRoles;
	}

}
