package com.sharetravel.domain.travelkeyword.controller;

import com.sharetravel.domain.travelkeyword.dto.TravelKeywordResponseDto;
import com.sharetravel.domain.travelkeyword.service.TravelKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TravelKeywordController {

    private final TravelKeywordService travelKeywordService;

    @GetMapping("/api/travelkeywords")
    public List<TravelKeywordResponseDto> findAll() {
        return travelKeywordService.getTravelKeywords();
    }
}
