package com.sharetravel.domain.travelkeyword.service;

import com.sharetravel.domain.travelkeyword.dto.TravelKeywordResponseDto;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TravelKeywordService {

    private final TravelKeywordRepository travelKeywordRepository;

    @Transactional(readOnly = true)
    public List<TravelKeywordResponseDto> getTravelKeywords() {
        return travelKeywordRepository.findAll()
                .stream()
                .map(TravelKeywordResponseDto::from)
                .collect(Collectors.toList());
    }
}
