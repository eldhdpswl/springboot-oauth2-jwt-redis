package com.example.springbootjwt.domain.domain;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

@Getter
public class AuthAccount extends User {

    /*
    Account 객체로 직접 받을 수 있지만 다음과 같은 이유로 어댑터 패턴을 사용한다.
        정보 객체로 사용되는 객체는 UserDetails를 구현하는 User를 상속받아야 한다.
        왜? loadUserByUsername메서드의 반환 타입이 UserDetails이기 때문이다.
        account에 UserDetails를 직접 구현하면 도메인 객체는 특정 기술에 종속되지 않도록 개발하는 Best Practice(모범 사례)에 위반하게 된다.


    User를 상속받는 UserAccount를 통해 커스텀한 Principal를 사용할 수 있게 되었다.
    그래서 인증을 하는 loadUserByUsername에서 Principal(UserDetails) 대신 위에서 만든 UserAccount를 리턴을 한다.
    *
    * */

    private final Account account;

    public AuthAccount(Account account) {
        super(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority(account.getRole())));
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthAccount that = (AuthAccount) o;
        return Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account);
    }
}