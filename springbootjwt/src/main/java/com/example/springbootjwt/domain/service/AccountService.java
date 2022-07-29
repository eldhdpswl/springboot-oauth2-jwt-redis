package com.example.springbootjwt.domain.service;

import com.example.springbootjwt.domain.domain.Account;
import com.example.springbootjwt.domain.domain.AccountRepository;
import com.example.springbootjwt.domain.domain.dto.LoginResponseDto;
import com.example.springbootjwt.global.common.exception.BadRequestException;
import com.example.springbootjwt.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    /*
    * 기존에는 간단하게 AccessToken을 만들어 바로 응답하였는데 이제는 RefreshToken까지 생성한 뒤, LoginResponseDto를 만들어 응답한다.
      추가로, 기존에는 jwtProvider의 createToken 메서드를 호출하여 AccessToken을 발급하였으나
      RefreshToken의 발급 또한 비슷한 과정을 통해 발급되기 때문에 재사용성을 고려하여 아래와 같이 수정하였다.
    * */

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(String email, String nickname, String password) {
        checkEmailIsDuplicate(email);
        checkPasswordConvention(password);
        String encodedPassword = passwordEncoder.encode(password);
        Account newAccount = Account.of(email, nickname, encodedPassword);
        accountRepository.save(newAccount);
    }

    public LoginResponseDto reIssueAccessToken(String email, String refreshToken) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("존재하지 않는 유저입니다."));
        jwtProvider.checkRefreshToken(email, refreshToken);
        String accessToken = jwtProvider.createAccessToken(account.getEmail(), account.getRole());
        return new LoginResponseDto(accessToken, refreshToken);
    }

    private void checkEmailIsDuplicate(String email) {
        boolean isDuplicate = accountRepository.existsByEmail(email);
        if(isDuplicate) {
            throw new BadRequestException("이미 존재하는 회원입니다.");
        }
    }

    public LoginResponseDto login(String email, String password) {
        Account account = accountRepository
                .findByEmail(email).orElseThrow(() -> new BadRequestException("아이디 혹은 비밀번호를 확인하세요."));
        checkPassword(password, account.getPassword());
        String accessToken = jwtProvider.createAccessToken(account.getEmail(), account.getRole());
        String refreshToken = jwtProvider.createRefreshToken(account.getEmail(), account.getRole());
        return new LoginResponseDto(accessToken, refreshToken);
//        return jwtProvider.createToken(account.getEmail(), account.getRole());
    }

    public void logout(String email, String accessToken) {
        jwtProvider.logout(email, accessToken);
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean isSame = passwordEncoder.matches(password, encodedPassword);
        if(!isSame) {
            throw new BadRequestException("아이디 혹은 비밀번호를 확인하세요.");
        }
    }

    private void checkPasswordConvention(String password) {
        // TODO: Check Password Convention
    }
}