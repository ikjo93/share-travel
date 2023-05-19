package com.sharetravel.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@ToString
public class UserInfoRegisterForm {

    @Length(min = 6, max = 15)
    private String nickName;

    @NotNull
    @Size(min = 1, max = 3)
    private List<Long> travelKeywords;
}
