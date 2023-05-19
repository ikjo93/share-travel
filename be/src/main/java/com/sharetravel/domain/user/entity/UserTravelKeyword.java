package com.sharetravel.domain.user.entity;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    private UserTravelKeyword(User user, TravelKeyword travelKeyword) {
        this.user = user;
        this.travelKeyword = travelKeyword;
    }

    public static UserTravelKeyword from(User user, TravelKeyword travelKeyword) {
        return new UserTravelKeyword(user, travelKeyword);
    }

    public String getTravelKeyWordName() {
        return travelKeyword.getName();
    }
}
