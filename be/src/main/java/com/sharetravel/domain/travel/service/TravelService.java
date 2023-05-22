package com.sharetravel.domain.travel.service;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.image.service.ImageService;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
import com.sharetravel.domain.travel.entity.Travel;
import com.sharetravel.domain.travel.repository.TravelRepository;
import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TravelService {

    private final ImageService imageService;

    private final TravelRepository travelRepository;
    private final TravelKeywordRepository travelKeywordRepository;
    private final UserRepository userRepository;

    @Transactional
    public TravelResponseDto save(Long userId, TravelRequestDto travelInfo) {
        User user = getUserById(userId);
        TravelKeyword travelKeyword = getTravelKeywordById(travelInfo.getTravelKeywordId());
        List<Image> images = imageService.uploadFiles(travelInfo.getImages());
        Travel travel = Travel.builder()
                .name(travelInfo.getName())
                .description(travelInfo.getDescription())
                .travelKeyword(travelKeyword)
                .writer(user)
                .location(travelInfo.getPoint())
                .build();

        for (Image image : images) {
            travel.addImage(image);
        }

        travelRepository.save(travel);

        return TravelResponseDto.from(travel);
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalStateException("식별 변호가" + userId + "에 해당하는 사용자가 없습니다.");
        });
    }

    private TravelKeyword getTravelKeywordById(Long travelKeywordId) {
        return travelKeywordRepository.findById(travelKeywordId).orElseThrow(() -> {
            throw new IllegalStateException("식별 변호가" + travelKeywordId + "에 해당하는 여행 키워드가 없습니다.");
        });
    }
}
