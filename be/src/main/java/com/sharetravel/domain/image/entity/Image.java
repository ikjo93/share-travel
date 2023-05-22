package com.sharetravel.domain.image.entity;

import com.sharetravel.domain.travel.entity.Travel;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
@Entity
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_travel_id")
    private Travel travel;

    @Column(length = 500, nullable = false)
    private String url;

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    private Image(String url) {
        this.url = url;
    }

    public static Image getImage(String url) {
        return new Image(url);
    }
}
