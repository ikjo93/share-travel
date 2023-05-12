package com.sharetravel.global.auth.oauth2.dto;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class CustomOAuth2User extends DefaultOAuth2User {

    private final String userId;
    private final String nickName;

    public CustomOAuth2User(
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> attributes, String nameAttributeKey, Long userId, String nickName) {
        super(authorities, attributes, nameAttributeKey);
        this.userId = String.valueOf(userId);
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }
}
