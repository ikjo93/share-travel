package com.sharetravel.domain.travelreview.entity;

import com.sharetravel.domain.travel.entity.Travel;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`travel_review`")
@Entity
public class TravelReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_review_id")
    private Long id;

    @Column(length = 300, nullable = false)
    private String comment;

    @Min(1)
    @Max(5)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_review_user_id", nullable = false)
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_review_travel_id", nullable = false)
    private Travel travel;
}
