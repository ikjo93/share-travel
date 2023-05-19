package com.sharetravel.domain.user.dto;

import com.sharetravel.domain.travelkeyword.dto.TravelKeywordResponseDto;
import com.sharetravel.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private Long userId;
    private String name;
    private String nickName;
    private String email;
    private String picture;
    private String oauthProvider;
    private List<TravelKeywordResponseDto> keywords;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(), user.getName(), user.getNickName(), user.getEmail(),
                user.getPicture(), user.getProvider().name(), user.getTravelKeywordsOfUser()
        );
    }
}
