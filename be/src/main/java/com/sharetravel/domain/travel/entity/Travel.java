package com.sharetravel.domain.travel.entity;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelreview.entity.TravelReview;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`travel`")
@Entity
public class Travel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(nullable = false)
    private Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_user_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_travel_keyword_id", nullable = false)
    private TravelKeyword travelKeyword;

    @OneToMany(mappedBy = "travel")
    private List<TravelReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    private List<Image> images = new ArrayList<>();

    @Builder
    public Travel(String name, String description, Point location, User writer,
                  TravelKeyword travelKeyword, List<TravelReview> reviews, List<Image> images) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.writer = writer;
        this.travelKeyword = travelKeyword;
        this.reviews = reviews;
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
        image.setTravel(this);
    }

    public String getTravelKeywordName() {
        return travelKeyword.getName();
    }

    public List<String> getImageUrls() {
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());
    }

    public Double getLongitude() {
        return location.getY();
    }

    public Double getLatitude() {
        return location.getY();
    }
}
