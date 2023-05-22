package com.sharetravel.domain.travel.controller;

import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.service.TravelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TravelController {

    private final TravelService travelService;

    @PostMapping("/api/travels")
    public TravelResponseDto save(@AuthenticationPrincipal Long userId, @Valid @RequestBody TravelRequestDto travelInfo) {
        return travelService.save(userId, travelInfo);
    }
}
