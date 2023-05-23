package com.sharetravel.domain.travel.repository;

import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    String SEARCH_RADIUS = "1000";

    String SELECT_TRAVEL_QUERY = "select " +
            "t.name as name, t.description as description, " +
            "tk.name as travelKeyword, MAX(i.url) as url, " +
            "ST_X(t.location) as latitude, ST_Y(t.location) as longitude " +
            "from travel t " +
            "join travel_keyword tk on t.travel_travel_keyword_id = tk.travel_keyword_id " +
            "join image i on t.travel_id = i.image_travel_id " +
            "where ST_Distance_Sphere(t.location, ST_GeomFromText(:point)) <= " + SEARCH_RADIUS + " " +
            "group by t.travel_id";

    @Query(value = SELECT_TRAVEL_QUERY, nativeQuery = true)
    List<TravelResponseDto> findAllByPoint(@Param("point") String point);

    String INSERT_TRAVEL_QUERY = "insert into travel (travel_travel_keyword_id, travel_user_id, name, description, location, created_date, modified_date) " +
            "values (:travelKeywordId, :userId, :name, :description, ST_GeomFromText(:point), now(), now())";

    @Modifying
    @Query(value = INSERT_TRAVEL_QUERY, nativeQuery = true)
    @Transactional
    void save(
            @Param("travelKeywordId") Long travelKeywordId,
            @Param("userId") Long userId,
            @Param("name") String name,
            @Param("description") String description,
            @Param("point") String point
    );

    @Query(value = "select travel_id from travel order by travel_id desc limit 1", nativeQuery = true)
    Long findLastTravelId();
}
