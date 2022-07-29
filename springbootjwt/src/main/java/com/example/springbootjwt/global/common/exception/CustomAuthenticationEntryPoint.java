package com.example.springbootjwt.global.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springbootjwt.global.common.dto.BasicResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
    * 토큰 인증에 대한 예외를 처리하는 EntryPoint를 작성한다.
      jwtProvider에서 토근 검증에 실패하면 발생하는 예외를 catch하여 request의 attribute에 exception을 추가했다.
      이제 추가했던 exception을 꺼내어 이에 대한 처리를 진행한다.
    * */

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String) request.getAttribute("exception");
        setResponse(response);
        BasicResponse exceptionDto = new BasicResponse(exception, HttpStatus.FORBIDDEN);
        response.getWriter().print(convertObjectToJson(exceptionDto));
    }

    private void setResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return mapper.writeValueAsString(object);
    }
}
