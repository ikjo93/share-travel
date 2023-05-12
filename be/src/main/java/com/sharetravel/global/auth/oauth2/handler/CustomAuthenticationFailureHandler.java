package com.sharetravel.global.auth.oauth2.handler;

import com.sharetravel.global.ApiResponseCode;
import com.sharetravel.global.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        System.out.println(exception.getCause());
        System.out.println(exception.getMessage());
        response.setStatus(ApiResponseCode.OAUTH2_LOGIN_FAIL.getHttpStatusCode());
        ServletUtil.setApiResponse(response, ApiResponseCode.OAUTH2_LOGIN_FAIL);
    }
}
