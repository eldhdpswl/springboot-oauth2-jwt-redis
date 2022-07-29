package com.example.springbootjwt.domain.service;

import com.example.springbootjwt.domain.domain.Account;
import com.example.springbootjwt.domain.domain.AccountRepository;
import com.example.springbootjwt.domain.domain.AuthAccount;
import com.example.springbootjwt.global.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAccountDetailsService implements UserDetailsService {

    /*
    * Authentication 객체를 만들기 위한 UserDetails 객체를 반환하는 CustomAccountDetailsService를 작성한다.
        UserDetailsService를 구현하여 작성된다.
        loadUserByUsername메서드를 구현하며 email을 전달받아 해당 이메일에 해당되는 Account객체를 통해 AuthAccount를 반환한다.

    * */

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("토큰을 확인하세요."));
        return new AuthAccount(account);
    }
}