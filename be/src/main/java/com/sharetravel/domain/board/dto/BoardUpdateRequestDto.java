package com.sharetravel.domain.board.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class BoardUpdateRequestDto {

    @Length(min = 1, max = 50)
    private String title;

    @Length(min = 1, max = 100)
    private String subTitle;

    @Length(min = 1, max = 1500)
    private String content;
}
