package com.sharetravel.global.auth.jwt.service;

import com.sharetravel.global.auth.jwt.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class TokenService {

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    protected static final Key SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_SECRET")));
    protected static final String INVALIDATE = "invalidate";

    protected Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    protected String makeToken(String userId, long expiration) {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setSubject(userId)
            .claim(INVALIDATE, false)
            .setIssuedAt(new Date())
            .setExpiration(new Date(expiration))
            .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
            .compact();
    }

    protected String remakeToken(String token, long expiration) {
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(getClaims(token))
            .setIssuedAt(new Date())
            .setExpiration(new Date(expiration))
            .signWith(SECRET_KEY, SIGNATURE_ALGORITHM)
            .compact();
    }
}
