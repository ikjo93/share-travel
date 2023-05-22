package com.sharetravel.domain.travel.repository;

import com.sharetravel.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {

}
