package com.sharetravel.domain.user.entity;

import com.sharetravel.domain.board.entity.Board;
import com.sharetravel.domain.travelkeyword.dto.TravelKeywordResponseDto;
import com.sharetravel.global.domain.BaseTimeEntity;
import com.sharetravel.global.auth.oauth2.dto.OAuth2Provider;
import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(
    name = "User.withAll",
    attributeNodes = {
        @NamedAttributeNode(value = "userTravelKeywords", subgraph = "userTravelKeywords")
    },
    subgraphs = @NamedSubgraph(
        name = "userTravelKeywords",
        attributeNodes = {
            @NamedAttributeNode("travelKeyword")
        }
    )
)
@Table(name = "`user`", indexes = {
    @Index(name = "index_email_provider", columnList = "email, provider", unique = true),
    @Index(name = "index_nickname", columnList = "nickname", unique = true)
})
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 15, name = "nickname")
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(length = 500, name = "picture")
    private String picture;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserTravelKeyword> userTravelKeywords = new ArrayList<>();

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

    public void clearUserTravelKeyword() {
        for (UserTravelKeyword userTravelKeyword : userTravelKeywords) {
            userTravelKeyword.setUser(null);
        }
        userTravelKeywords.clear();
    }

    public void addUserTravelKeyword(UserTravelKeyword userTravelKeyword) {
        userTravelKeywords.add(userTravelKeyword);
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<TravelKeywordResponseDto> getTravelKeywords() {
        return userTravelKeywords.stream()
                .map(UserTravelKeyword::getTravelKeyword)
                .map(TravelKeywordResponseDto::from)
                .collect(Collectors.toList());
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public List<Long> getIdsOfUserTravelKeyword() {
        return userTravelKeywords.stream()
            .map(UserTravelKeyword::getId)
            .collect(Collectors.toList());
    }
}
