package com.sharetravel.domain.travel.service;

import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final TravelRepository travelRepository;

    @Transactional
    public TravelResponseDto save(Long userId, TravelRequestDto requestDto) {
        return null;
    }
}
