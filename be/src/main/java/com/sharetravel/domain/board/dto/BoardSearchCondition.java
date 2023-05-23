package com.sharetravel.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardSearchCondition {

    @NotNull
    private Long categoryId;

    @Pattern(regexp = "^(title|author)$", message = "searchType 의 파라미터 값은 title 또는 author 이어야 합니다.")
    private String searchType;

    @NotBlank
    private String keyword;

    public boolean isTitle() {
        return searchType.equals("title");
    }
}
