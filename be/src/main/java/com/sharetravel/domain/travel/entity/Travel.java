package com.sharetravel.domain.travel.entity;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelreview.entity.TravelReview;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ToString
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
}
