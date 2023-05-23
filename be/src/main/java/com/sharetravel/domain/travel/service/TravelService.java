package com.sharetravel.domain.travel.service;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.image.repository.ImageRepository;
import com.sharetravel.domain.image.service.ImageService;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.dto.TravelSearchResponseDto;
import com.sharetravel.domain.travel.entity.Travel;
import com.sharetravel.domain.travel.repository.TravelRepository;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import java.util.List;

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
        Travel travel = travelRepository.findWithAllById(travelId).orElseThrow(() -> {
            throw new IllegalStateException("식별 변호가 " + travelId + "에 해당하는 여행지 정보가 없습니다.");
        });

        return TravelResponseDto.from(travel);
    }

    @Transactional(readOnly = true)
    public List<TravelSearchResponseDto> findAllAroundCoordinate(Double longitude, Double latitude) {
        String point = getPoint(longitude, latitude);
        return travelRepository.findAllByPoint(point);
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
