package com.sharetravel.domain.travel.controller;

import com.sharetravel.domain.image.exception.ImageUploadException;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.service.TravelService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import com.sharetravel.global.api.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class TravelController {

    private final TravelService travelService;

    @PostMapping("/api/travels")
    public TravelResponseDto save(
        @AuthenticationPrincipal Long userId,
        @Valid @ModelAttribute TravelRequestDto travelInfo) {
        return travelService.save(userId, travelInfo);
    }

    @ExceptionHandler(value = {ImageUploadException.class, ResponseStatusException.class})
    public ResponseEntity<ApiResponseMessage> handleTravelSavingException() {
        return ApiUtil.getResponseEntity(ApiResponseCode.TRAVEL_SAVING_FAILED);
    }
}
