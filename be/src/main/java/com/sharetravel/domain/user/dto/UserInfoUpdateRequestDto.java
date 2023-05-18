package com.sharetravel.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
public class UserInfoUpdateRequestDto {

    @Length(min = 6, max = 15)
    private String nickName;
    private String picture;

    @NotNull
    @Size(min = 1, max = 3)
    private Set<Long> travelKeywords;
}
