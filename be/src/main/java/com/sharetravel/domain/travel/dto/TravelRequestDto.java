package com.sharetravel.domain.travel.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.geo.Point;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelRequestDto {

    @Length(min = 1, max = 30)
    private String name;

    @Length(min = 1, max = 500)
    private String description;

    @NotNull
    private Long travelKeywordId;

    @Size(min = 1, max = 3)
    private List<MultipartFile> files;

    @NotNull
    private Double longitude; // 위도 127...

    @NotNull
    private Double latitude; // 경도 37...

    // the x field represents the longitude, and the y field represents the latitude.
    public Point getPoint() {
        return new Point(longitude, latitude);
    }
}
