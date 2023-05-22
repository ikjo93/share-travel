package com.sharetravel.domain.travelkeyword.entity;

import com.sharetravel.domain.user.entity.UserTravelKeyword;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "`travel_keyword`")
@Entity
public class TravelKeyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_keyword_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "travelKeyword", cascade = CascadeType.REMOVE)
    private List<UserTravelKeyword> userTravelKeywords = new ArrayList<>();

    public void addUserTravelKeyword(UserTravelKeyword userTravelKeyword) {
        userTravelKeywords.add(userTravelKeyword);
    }
}
