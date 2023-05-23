package com.sharetravel.domain.travel.dto;

public interface TravelResponseDto {

    String getName();

    String getDescription();

    String getTravelKeyword();

    String getUrl();

    Double getLongitude(); // 경도

    Double getLatitude(); // 위도
}
