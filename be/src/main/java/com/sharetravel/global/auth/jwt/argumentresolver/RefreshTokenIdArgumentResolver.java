package com.sharetravel.global.auth.jwt.argumentresolver;

import static com.sharetravel.global.auth.jwt.utils.TokenUtils.parseRefreshTokenId;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RefreshTokenIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasRefreshTokenIdAnnotation = parameter.hasParameterAnnotation(RefreshTokenId.class);
        boolean hasRefreshIdTokenType = String.class.isAssignableFrom(parameter.getParameterType());

        return hasRefreshTokenIdAnnotation && hasRefreshIdTokenType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        return parseRefreshTokenId(request);
    }
}
