package com.sharetravel.domain.travel.service;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.image.repository.ImageRepository;
import com.sharetravel.domain.image.service.ImageService;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.dto.TravelSearchResponseDto;
import com.sharetravel.domain.travel.repository.TravelRepository;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final ImageService imageService;

    private final TravelRepository travelRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final TravelKeywordRepository travelKeywordRepository;

    @Transactional(readOnly = true)
    public TravelResponseDto findById(Long travelId) {
        TravelSearchResponseDto travel = travelRepository.findWithAllById(travelId);
        List<String> urls = travelRepository.findImagesById(travelId);

        return TravelResponseDto.from(travel, urls);
    }

    @Transactional(readOnly = true)
    public List<TravelSearchResponseDto> findAllAroundCoordinate(Double longitude, Double latitude) {
        String point = getPoint(longitude, latitude);
        List<TravelSearchResponseDto> travels = travelRepository.findAllByPoint(point);
        List<TravelKeyword> travelKeywords = travelKeywordRepository.findAll();
        Map<Long, String> travelKeywordMap = getTravelKeywordMap(travelKeywords);
        setKeywordByTravel(travels, travelKeywordMap);

        return travels;
    }

    private Map<Long, String> getTravelKeywordMap(List<TravelKeyword> travelKeywords) {
        Map<Long, String> travelKeywordMap = new HashMap<>();
        for (TravelKeyword travelKeyword : travelKeywords) {
            travelKeywordMap.put(travelKeyword.getId(), travelKeyword.getName());
        }

        return travelKeywordMap;
    }

    private void setKeywordByTravel(List<TravelSearchResponseDto> travels, Map<Long, String> travelKeywordMap) {
        for (TravelSearchResponseDto travel : travels) {
            travel.setTravelKeyword(travelKeywordMap.get(travel.getTravelKeywordId()));
        }
    }

    @Transactional(readOnly = true)
    public List<TravelSearchResponseDto> findAllAroundCoordinateByKeywordId(Long keywordId, Double longitude, Double latitude) {
        String point = getPoint(longitude, latitude);
        return travelRepository.findAllByKeywordIdAndPoint(keywordId, point);
    }

    @Transactional
    public void save(Long userId, TravelRequestDto travelInfo) {
        User user = getUserById(userId);
        TravelKeyword travelKeyword = getTravelKeywordById(travelInfo.getTravelKeywordId());
        String point = getPoint(travelInfo.getLongitude(), travelInfo.getLatitude());
        travelRepository.save(travelKeyword.getId(), user.getId(), travelInfo.getName(), travelInfo.getDescription(), point);

        Long travelId = travelRepository.findLastTravelId();

        List<Image> images = imageService.uploadFiles(travelInfo.getFiles());

        for (Image image : images) {
            imageRepository.save(travelId, image.getUrl());
        }
    }

    private String getPoint(Double longitude, Double latitude) {
        return String.format("POINT(%s %s)", longitude, latitude);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalStateException("식별 변호가 " + userId + "에 해당하는 사용자가 없습니다.");
        });
    }

    private TravelKeyword getTravelKeywordById(Long travelKeywordId) {
        return travelKeywordRepository.findById(travelKeywordId).orElseThrow(() -> {
            throw new IllegalStateException("식별 변호가 " + travelKeywordId + "에 해당하는 여행 키워드가 없습니다.");
        });
    }
}
