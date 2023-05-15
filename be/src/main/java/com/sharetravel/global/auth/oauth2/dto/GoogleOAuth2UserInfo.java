package com.sharetravel.global.auth.oauth2.dto;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    private final String userId;
    private final String nickName;
    private static final String KEY_ID = "sub";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PICTURE = "picture";

    public GoogleOAuth2UserInfo(String userId, String nickName, Map<String, Object> attributes) {
        super(OAuth2Provider.GOOGLE, attributes);
        this.userId = userId;
        this.nickName = nickName;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public Long getId() {
        return (Long) this.attributes.get(KEY_ID);
    }

    @Override
    public String getName() {
        return (String) this.attributes.get(KEY_NAME);
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public String getEmail() {
        return (String) this.attributes.get(KEY_EMAIL);
    }

    @Override
    public String getPicture() {
        return (String) this.attributes.get(KEY_PICTURE);
    }

    @Override
    public Map<String, Object> getEtcData() {
        return this.attributes;
    }
}