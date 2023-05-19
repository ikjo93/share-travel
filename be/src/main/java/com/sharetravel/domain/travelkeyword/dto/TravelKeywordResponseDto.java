package com.sharetravel.domain.travelkeyword.dto;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelKeywordResponseDto {

    private Long id;
    private String name;

    public static TravelKeywordResponseDto from(TravelKeyword travelKeyword) {
        return new TravelKeywordResponseDto(travelKeyword.getId(), travelKeyword.getName());
    }
}
