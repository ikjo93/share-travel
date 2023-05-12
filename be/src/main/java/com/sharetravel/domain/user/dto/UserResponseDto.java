package com.sharetravel.domain.user.dto;

import com.sharetravel.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponseDto {

    private Long userId;
    private String name;
    private String nickName;
    private String email;
    private String picture;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getNickName(), user.getEmail(), user.getPicture());
    }
}
