package com.sharetravel.domain.user.entity;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_travel_keyword")
@Entity
public class UserTravelKeyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_travel_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_keyword_id")
    private TravelKeyword travelKeyword;

    @Builder
    public UserTravelKeyword(User user, TravelKeyword travelKeyword) {
        this.user = user;
        this.travelKeyword = travelKeyword;
    }

    public String getTravelKeyWordName() {
        return travelKeyword.getName();
    }
}
