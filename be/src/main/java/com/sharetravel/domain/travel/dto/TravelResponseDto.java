package com.sharetravel.domain.travel.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelResponseDto {

    private String name;
    private String description;
    private String travelKeyword;
    private String writer;
    private List<String> urls;

    public static TravelResponseDto from(TravelSearchResponseDto travel, List<String> urls) {
        return new TravelResponseDto(travel.getName(), travel.getDescription(),
            travel.getTravelKeyword(), travel.getUserNickName(), urls);
    }
}
