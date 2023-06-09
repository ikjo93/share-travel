package com.sharetravel.domain.travel.repository;

import com.sharetravel.domain.travel.dto.TravelSearchResponseDto;
import com.sharetravel.domain.travel.entity.Travel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {

    String SELECT_TRAVEL_INFO_QUERY = "select t.travel_id as travelId, " +
            "t.name as name, t.description as description, u.nickname userNickName, " +
            "tk.name as travelKeyword, ST_X(t.location) as longitude, ST_Y(t.location) as latitude " +
            "from travel t " +
            "join user u on t.travel_user_id = u.user_id " +
            "join travel_keyword tk on t.travel_travel_keyword_id = tk.travel_keyword_id " +
            "where t.travel_id = :id";

    @Query(value = SELECT_TRAVEL_INFO_QUERY, nativeQuery = true)
    TravelSearchResponseDto findWithAllById(@Param("id") Long id);

    String SELECT_TRAVEL_IMAGES_QUERY = "select url " +
            "from travel t " +
            "join image i on t.travel_id = i.image_travel_id " +
            "where t.travel_id = :id";

    @Query(value = SELECT_TRAVEL_IMAGES_QUERY, nativeQuery = true)
    List<String> findImagesById(@Param("id") Long id);

    String SEARCH_DEGREE = "0.009";

    String SELECT_TRAVEL_QUERY = "select t.travel_id as travelId, " +
            "ST_X(t.location) as longitude, ST_Y(t.location) as latitude " +
            "from travel t " +
            "where ST_Contains(ST_Buffer(ST_GeomFromText(:point), " + SEARCH_DEGREE + "), t.location)";

    @Query(value = SELECT_TRAVEL_QUERY, nativeQuery = true)
    List<TravelSearchResponseDto> findAllByPoint(@Param("point") String point);

    String SEARCH_METER = "1000";

    String SELECT_TRAVEL_BY_KEYWORD_QUERY = "select t.travel_id as travelId, " +
        "t.name as name, t.description as description, " +
        "tk.name as travelKeyword, MAX(i.url) as url, " +
        "ST_X(t.location) as longitude, ST_Y(t.location) as latitude " +
        "from travel t " +
        "join travel_keyword tk on t.travel_travel_keyword_id = tk.travel_keyword_id " +
        "join image i on t.travel_id = i.image_travel_id " +
        "where ST_Distance_Sphere(t.location, ST_GeomFromText(:point)) <= " + SEARCH_METER + " " +
        "and t.travel_travel_keyword_id = :keywordId " +
        "group by t.travel_id";

    @Query(value = SELECT_TRAVEL_BY_KEYWORD_QUERY, nativeQuery = true)
    List<TravelSearchResponseDto> findAllByKeywordIdAndPoint(@Param("keywordId") Long keywordId, @Param("point") String point);

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
