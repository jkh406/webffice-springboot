package com.anbtech.webffice.global.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.anbtech.webffice.global.filter.CorsFilter;
import com.anbtech.webffice.global.jwt.JwtAccessDeniedHandler;
import com.anbtech.webffice.global.jwt.JwtAuthenticationEntryPoint;
import com.anbtech.webffice.global.jwt.JwtSecurityConfig;
import com.anbtech.webffice.global.jwt.TokenProvider;

import jakarta.servlet.Filter;

@EnableWebSecurity
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	public SecurityConfig(
		TokenProvider tokenProvider,
		CorsFilter corsFilter,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler
	
	){
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .requestMatchers("/api/v1/get").permitAll()
            .requestMatchers("/api/v1/login").permitAll()
            .requestMatchers("/api/v1/join").hasRole("USER")
        	.requestMatchers("/**");
//        http.formLogin();
//        http.csrf().disable();
//        http.exceptionHandling();
//		http.httpBasic().disable();
//		http.sessionManagement()
//		 	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // jwt token으로 인증하므로 stateless 하도록 처리.
//		http.addFilterBefore((Filter) corsFilter, UsernamePasswordAuthenticationFilter.class);
//		http.httpBasic().authenticationEntryPoint(jwtAuthenticationEntryPoint);
//		http.apply(new JwtSecurityConfig(tokenProvider));
//		http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
		http.cors().configurationSource(corsConfigurationSource());
		    
      
      return http.build();
	}
    
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
//        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return (CorsConfigurationSource) source;
	}
	


}
