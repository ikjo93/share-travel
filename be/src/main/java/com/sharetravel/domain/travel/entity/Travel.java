package com.sharetravel.domain.travel.entity;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.stream.Collectors;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NamedEntityGraph(name = "Travel.withAll", attributeNodes = {
    @NamedAttributeNode("writer"), @NamedAttributeNode("travelKeyword"),
    @NamedAttributeNode("images")
})
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
    private List<Image> images = new ArrayList<>();

    public String getTravelKeyword() {
        return travelKeyword.getName();
    }

    public String getWriterNickName() {
        return writer.getNickName();
    }

    public List<String> getImageUrls() {
        return images.stream()
            .map(Image::getUrl)
            .collect(Collectors.toList());
    }
}
