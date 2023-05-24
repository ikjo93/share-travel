package com.sharetravel.domain.image.repository;

import com.sharetravel.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    String INSERT_IMAGE_QUERY = "insert into image " +
            "(image_travel_id, url, created_date, modified_date) " +
            "values (:travelId, :url, now(), now())";

    @Modifying
    @Query(value = INSERT_IMAGE_QUERY, nativeQuery = true)
    @Transactional
    void save(@Param("travelId") Long travelId, @Param("url") String url);
}
