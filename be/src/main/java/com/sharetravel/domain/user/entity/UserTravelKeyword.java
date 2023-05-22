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
        user.addUserTravelKeyword(this);
        this.user = user;
        travelKeyword.addUserTravelKeyword(this);
        this.travelKeyword = travelKeyword;
    }

    public static UserTravelKeyword createUserTravelKeyword(User user, TravelKeyword travelKeyword) {
        return new UserTravelKeyword(user, travelKeyword);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
