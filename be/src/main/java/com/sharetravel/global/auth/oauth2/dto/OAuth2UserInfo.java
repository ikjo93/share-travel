package com.sharetravel.global.auth.oauth2.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public abstract class OAuth2UserInfo {

    protected OAuth2Provider provider;
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(OAuth2Provider provider, Map<String, Object> attributes) {
        this.provider = provider;
        this.attributes = attributes;
    }

    public abstract String getUserId();
    public abstract Long getId();
    public abstract String getName();
    public abstract String getNickName();
    public abstract String getEmail();
    public abstract String getPicture();
    public abstract Map<String, Object> getEtcData();
}
