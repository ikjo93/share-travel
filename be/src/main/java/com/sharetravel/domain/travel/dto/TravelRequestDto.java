package com.sharetravel.domain.travel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.geo.Point;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class TravelRequestDto {

    @Length(min = 1, max = 30)
    private String name;

    @Length(min = 1, max = 500)
    private String description;

    @NotNull
    private Long travelKeywordId;

    @Size(max = 3)
    private List<MultipartFile> images;

    @NotNull
    private Double latitude; // 위도

    @NotNull
    private Double longitude; // 경도

    public Point getPoint() {
        return new Point(longitude, latitude);
    }
}
