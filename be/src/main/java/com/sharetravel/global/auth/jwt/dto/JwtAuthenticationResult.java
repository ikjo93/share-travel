package com.sharetravel.global.auth.jwt.dto;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class JwtAuthenticationResult extends AbstractAuthenticationToken {

    private final Object principal;

    public JwtAuthenticationResult(Long userId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        principal = userId;
    }

    @Deprecated
    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
