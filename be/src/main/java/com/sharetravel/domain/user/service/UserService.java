package com.sharetravel.domain.user.service;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.dto.UserInfoRegisterForm;
import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserInfoUpdateRequestDto;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.entity.UserTravelKeyword;
import com.sharetravel.domain.user.repository.UserRepository;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TravelKeywordRepository travelKeywordRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        return UserResponseDto.from(getUserById(id));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponseMessage> validateDuplicate(String nickName) {
        if (userRepository.findByNickName(nickName).isPresent()) {
            return getResponseEntity(ApiResponseCode.USER_DUPLICATE_NICKNAME);
        } else {
            return getResponseEntity(ApiResponseCode.USER_NOT_DUPLICATE_NICKNAME);
        }
    }

    @Transactional
    public UserResponseDto register(Long userId, UserInfoRegisterForm userInfo) {
        User user = getUserById(userId);

        user.registerNickNameAndTravelKeywords(userInfo.getNickName(), getUserTravelKeywords(user, userInfo.getTravelKeywords()));

        return UserResponseDto.from(user);
    }

    private List<UserTravelKeyword> getUserTravelKeywords(User user, List<Long> travelKeywordIds) {
        List<UserTravelKeyword> userTravelKeywords = new ArrayList<>();
        for (Long travelKeywordId : travelKeywordIds) {
            TravelKeyword travelKeyword = travelKeywordRepository.findById(travelKeywordId).orElseThrow(() -> {
                throw new IllegalStateException("식별 번호가 " + travelKeywordId + "인 여행지 키워드가 존재하지 않습니다.");
            });
            userTravelKeywords.add(
                    UserTravelKeyword.builder()
                            .user(user)
                            .travelKeyword(travelKeyword)
                            .build()
            );
        }

        return userTravelKeywords;
    }

    @Transactional
    public UserResponseDto update(Long id, UserInfoUpdateRequestDto userInfo) {
        User user = getUserById(id);

        user.updateNickNameAndPicture(userInfo.getNickName(), userInfo.getPicture());

        return UserResponseDto.from(user);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
                });
    }
}
