package com.hyerijang.book.springboot.config.auth;

import com.hyerijang.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor //초기화 되지않은 final 필드나, @NonNull이 붙은 필드에 대한 생성자 생성
@EnableWebSecurity //스프링세큐리티 설정들 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void Configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()//h2-console 사용 위해 disable
                .headers().frameOptions().disable() //h2-console 사용 위해 disable
                .and()
                .authorizeRequests() //Url별 권한 관리 설정하는 옵션의 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll() //특정 url은 전체 열람권한(permitAll())
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // User권한을 가진 사람(hasRole(Role.USER.name())만 /api/v1/** 열람 가능
                .anyRequest().authenticated() //설정 된 url외 나머지 url에 관한 설정 (authenticated() 설정 시 인증된 사용자(로그인 유저)에게만 허용)
                .and()
                .logout() //로그아웃 기능에 대한 여러 설정의진입점
                .logoutSuccessUrl("/") //로그아웃 성공시 /로 이동
                .and()
                .oauth2Login() //OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올때의 설정 담당
                .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록. 리소스 서버(소셜 서버)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 할 수 있다.

    }
}
