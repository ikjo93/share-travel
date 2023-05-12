package com.sharetravel.global.auth.oauth2.dto;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    private final String userId;
    private final String nickName;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nickname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PICTURE = "profile_image";
    private static final String KEY_ETC_DATA = "kakao_account";

    public KakaoOAuth2UserInfo(String userId, String nickName, Map<String, Object> attributes) {
        super(OAuth2Provider.KAKAO, attributes);
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
        Map<String, Object> properties = (Map<String, Object>) this.attributes.get("properties");
        return (String) properties.get(KEY_NAME);
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) this.attributes.get("kakao_account");
        return (String) kakaoAccount.get(KEY_EMAIL);
    }

    @Override
    public String getPicture() {
        Map<String, Object> properties = (Map<String, Object>) this.attributes.get("properties");
        return (String) properties.get(KEY_PICTURE);
    }

    @Override
    public Map<String, Object> getEtcData() {
        return (Map<String, Object>) this.attributes.get(KEY_ETC_DATA);
    }
}
