package com.sharetravel.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class UserInfoUpdateRequestDto {

    @Size(min = 6)
    @Size(max = 30)
    private String nickName;
    private String picture;

    @NotNull
    @Size(min = 1, max = 3)
    private Set<Long> travelKeywords;
}
