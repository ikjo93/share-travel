package com.sharetravel.domain.travel.dto;

import com.sharetravel.domain.travel.entity.Travel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelResponseDto {

    private String name;

    private String description;

    private String travelKeyword;

    private List<String> images;
    private Double latitude; // 위도

    private Double longitude; // 경도

    public static TravelResponseDto from(Travel travel) {
        return new TravelResponseDto(travel.getName(), travel.getDescription(), travel.getTravelKeywordName(),
                travel.getImageUrls(), travel.getLatitude(), travel.getLongitude());
    }
}
