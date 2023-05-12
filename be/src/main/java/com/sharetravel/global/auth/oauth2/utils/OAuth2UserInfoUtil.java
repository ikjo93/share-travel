package com.sharetravel.global.auth.oauth2.utils;

import com.sharetravel.global.auth.oauth2.dto.CustomOAuth2User;
import com.sharetravel.global.auth.oauth2.dto.GoogleOAuth2UserInfo;
import com.sharetravel.global.auth.oauth2.dto.KakaoOAuth2UserInfo;
import com.sharetravel.global.auth.oauth2.dto.NaverOAuth2UserInfo;
import com.sharetravel.global.auth.oauth2.dto.OAuth2UserInfo;
import com.sharetravel.global.auth.oauth2.exception.UnsupportedOAuth2ProviderException;
import java.util.Map;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class OAuth2UserInfoUtil {

    public static OAuth2UserInfo getOAuth2UserInfo(OAuth2AuthenticationToken auth2AuthenticationToken) {
        String registrationId = auth2AuthenticationToken.getAuthorizedClientRegistrationId();
        CustomOAuth2User oAuth2User = (CustomOAuth2User) auth2AuthenticationToken.getPrincipal();
        String userId = oAuth2User.getUserId();
        String nickName = oAuth2User.getNickName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("google".equals(registrationId)) {
            return new GoogleOAuth2UserInfo(userId, nickName, attributes);
        } else if ("naver".equals(registrationId)) {
            return new NaverOAuth2UserInfo(userId, nickName, attributes);
        } else if ("kakao".equals(registrationId)) {
            return new KakaoOAuth2UserInfo(userId, nickName, attributes);
        }

        throw new UnsupportedOAuth2ProviderException();
    }
}
