package com.sharetravel.global.auth.oauth2.service;

import com.sharetravel.global.auth.oauth2.dto.CustomOAuth2User;
import com.sharetravel.global.auth.oauth2.dto.OAuthAttributes;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdate(oAuthAttributes);

        return new CustomOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
            attributes,
            oAuthAttributes.getNameAttributeKey(),
            user.getId(),
            user.getNickName()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmailAndProvider(attributes.getEmail(), attributes.getProvider())
            .orElse(attributes.toEntity());

        // user 의 식별자가 존재하지 않는 경우 insert
        // user 의 식별자가 존재하는 경우 select 후 data 변경 있으면 update, data 변경 없으면 종료
        return userRepository.save(user);
    }
}
