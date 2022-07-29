package com.example.springbootjwt.global.config;

import com.example.springbootjwt.global.common.exception.CustomAuthenticationEntryPoint;
import com.example.springbootjwt.global.config.jwt.JwtAuthenticationFilter;
import com.example.springbootjwt.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // @Configuration 어노테이션을 통해 AppConfig를 설정파일로 등록한다.
@EnableWebSecurity  //@EnableWebSecurity를 통해 Security 설정을 활성화시킨다.
@RequiredArgsConstructor
public class AppConfig {
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /*
    * PasswordEncoder
      회원가입을 할 때, 데이터베이스에 사용자가 전달해준 비밀번호를 그대로 저장하면 안된다.
      따라서 PasswordEncoder를 Bean으로 등록하여 회원가입 진행 시, 비밀번호를 해시하여 저장한다.
    * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    * SecurityFilterChain
      Spring Security는 FilterChain 방식으로 인증 플로우가 진행된다.
      cors와 csrf를 disable시킨다. (cors와 csrf가 뭔데?)
      authorizeRequests()를 통해 인증된 사용자만 접근할 수 있도록 설정한다.
      antMatchers를 통해 특정 URI는 누구나 접근 가능하도록 설정 및 'ADMIN'의 룰을 가지는 유저만 접근 할 수 있도록 설정 가능하다.
      기본적으로 UsernamePasswordAuthenticationFilter가 우선적으로 걸리는데 이 보다 앞에 추후에 직접 작성할 CustomJwtFilter를 설정하도록 할 예정이다.
    *
    * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 X
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/sign-up", "/api/v1/user/login").permitAll()
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}