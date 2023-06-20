package com.sharetravel.domain.travel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TravelSearchResponseDto {

    private Long travelId;
    private Long travelKeywordId;
    private String name;
    private String userNickName;
    private String description;
    private String travelKeyword;
    private String url;
    private Double longitude; // 경도
    private Double latitude; // 위도
}
