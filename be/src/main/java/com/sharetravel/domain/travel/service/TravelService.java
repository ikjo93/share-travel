package com.sharetravel.domain.travel.service;

import com.sharetravel.domain.image.entity.Image;
import com.sharetravel.domain.image.repository.ImageRepository;
import com.sharetravel.domain.image.service.ImageService;
import com.sharetravel.domain.travel.dto.TravelRequestDto;
import com.sharetravel.domain.travel.dto.TravelResponseDto;
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
    private final TravelKeywordRepository travelKeywordRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public TravelResponseDto save(Long userId, TravelRequestDto travelInfo) {
        User user = getUserById(userId);
        TravelKeyword travelKeyword = getTravelKeywordById(travelInfo.getTravelKeywordId());
        Travel travel = Travel.builder()
                .name(travelInfo.getName())
                .description(travelInfo.getDescription())
                .travelKeyword(travelKeyword)
                .writer(user)
                .location(travelInfo.getPoint())
                .build();

        List<Image> images = imageService.uploadFiles(travelInfo.getFiles());
        for (Image image : images) {
            travel.addImage(image);
        }

        // 실행 순서 중요 -> 불필요한 더티 체킹 유발 가능
        travelRepository.save(travel);
        imageRepository.saveAll(images);

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
