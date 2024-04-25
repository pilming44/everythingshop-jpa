package com.jpa.everythingshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * fileName : WebSecurityConfig
 * author   : pilming
 * date     : 2023-03-07
 */
@Configuration
@EnableWebSecurity //Spring Security 구성을 사용하도록 Spring Boot에 지시
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(antMatcher("/myPage/admin/**")).hasRole("01")// 특정 URL은 ADMIN 역할만 허용
                                .requestMatchers(antMatcher("/myPage/**")).authenticated()
                                .requestMatchers(antMatcher("/product/**/order")).authenticated()
                                .requestMatchers(antMatcher("/product/**/register")).authenticated()
                                .anyRequest().permitAll()
                )
                .formLogin((formLogin) ->
                        formLogin
                                .loginPage("/user/signIn")
                                .usernameParameter("userId")
                                .passwordParameter("userPw")
                                .loginProcessingUrl("/loginProc")
                                .successHandler(customAuthenticationSuccessHandler)     // 로그인 성공 핸들러
                                .failureHandler(customAuthenticationFailureHandler)     //로그인실패 핸들러
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.accessDeniedPage("/error")
                )
                .build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
