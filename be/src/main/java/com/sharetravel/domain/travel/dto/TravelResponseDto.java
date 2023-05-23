package com.sharetravel.domain.travel.dto;

import com.sharetravel.domain.travel.entity.Travel;
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
    private List<String> url;

    public static TravelResponseDto from(Travel travel) {
        return new TravelResponseDto(travel.getName(), travel.getDescription(),
            travel.getTravelKeyword(), travel.getWriterNickName(), travel.getImageUrls());
    }
}
