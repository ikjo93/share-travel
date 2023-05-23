package com.sharetravel.domain.travel.controller;

import com.sharetravel.domain.image.exception.ImageUploadException;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.dto.TravelSearchResponseDto;
import com.sharetravel.domain.travel.service.TravelService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

@RequiredArgsConstructor
@RestController
public class TravelController {

    private final TravelService travelService;

    @GetMapping("/api/travels")
    public List<TravelSearchResponseDto> findAround(@RequestParam Double longitude, @RequestParam Double latitude) {
        return travelService.findAllAroundCoordinate(longitude, latitude);
    }

    @GetMapping("/api/travels/{id}")
    public TravelResponseDto findById(@PathVariable Long id) {
        return travelService.findById(id);
    }

    @GetMapping("/api/travels")
    public List<TravelSearchResponseDto> findAroundByKeyword(@RequestParam Long keywordId, @RequestParam Double longitude, @RequestParam Double latitude) {
        return travelService.findAllAroundCoordinateByKeywordId(keywordId, longitude, latitude);
    }

    @PostMapping("/api/travels")
    public ResponseEntity<ApiResponseMessage> save(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute TravelRequestDto travelInfo) {
        travelService.save(userId, travelInfo);
        return getResponseEntity(ApiResponseCode.TRAVEL_SAVING_SUCCESS);
    }

    @ExceptionHandler(value = {ImageUploadException.class, ResponseStatusException.class})
    public ResponseEntity<ApiResponseMessage> handleTravelSavingException() {
        return getResponseEntity(ApiResponseCode.TRAVEL_SAVING_FAILED);
    }
}
