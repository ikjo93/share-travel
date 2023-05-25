package com.sharetravel.domain.board.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BoardSaveRequestDto {

    @Length(min = 6,max = 15)
    private String nickName;

    @Length(min = 1, max = 50)
    private String title;

    @Length(min = 1, max = 100)
    private String subTitle;

    @Length(min = 1, max = 1500)
    private String content;

    @Min(1)
    private Long categoryId;
}
