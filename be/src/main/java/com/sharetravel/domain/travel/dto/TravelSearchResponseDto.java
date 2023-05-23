package com.sharetravel.domain.travel.dto;

public interface TravelSearchResponseDto {

    Long getTravelId();

    String getName();

    String getDescription();

    String getTravelKeyword();

    String getUrl();

    Double getLongitude(); // 경도

    Double getLatitude(); // 위도
}
