package com.sharetravel.domain.user.entity;

import com.sharetravel.global.domain.BaseTimeEntity;
import com.sharetravel.global.auth.oauth2.dto.OAuth2Provider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`user`", indexes = @Index(name = "index_email_provider", columnList = "email, provider", unique = true))
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 30, name = "nickname")
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(length = 500, name = "picture")
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OAuth2Provider provider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String nickName, String email, String picture,
        OAuth2Provider provider, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
