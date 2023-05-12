package com.sharetravel.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {

    @Size(min = 6)
    @Size(max = 30)
    private String nickName;
    private String picture;
}
