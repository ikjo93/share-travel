package com.sharetravel.domain.travelkeyword.repository;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TravelKeywordRepository extends JpaRepository<TravelKeyword, Long> {

    @Query("SELECT tk FROM TravelKeyword tk WHERE tk.id IN (:ids)")
    List<TravelKeyword> findInIds(@Param("ids") List<Long> ids);
}
