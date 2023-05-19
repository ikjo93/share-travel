package com.sharetravel.domain.user.service;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserInfoRequestDto;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.entity.UserTravelKeyword;
import com.sharetravel.domain.user.repository.UserRepository;
import com.sharetravel.domain.user.repository.UserTravelKeywordRepository;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TravelKeywordRepository travelKeywordRepository;
    private final UserTravelKeywordRepository userTravelKeywordRepository;

    // 회원 탈퇴 처리를 위한 메일 발송 관련 의존성 주입
    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, Integer> redisTemplate;

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
    public UserResponseDto register(Long userId, UserInfoRequestDto userInfo) {
        User user = getUserById(userId);

        user.registerNickNameAndTravelKeywords(userInfo.getNickName(), getUserTravelKeywords(user, userInfo.getTravelKeywords()));

        return UserResponseDto.from(user);
    }

    private List<UserTravelKeyword> getUserTravelKeywords(User user, List<Long> travelKeywordIds) {
        List<TravelKeyword> travelKeywords = travelKeywordRepository.findInIds(travelKeywordIds);
        if (travelKeywordIds.size() != travelKeywords.size()) {
            throw new IllegalStateException("존재하지 않는 여행지 키워드입니다.");
        }

        List<UserTravelKeyword> userTravelKeywords = new ArrayList<>();
        for (TravelKeyword travelKeyword : travelKeywords) {
            userTravelKeywords.add(UserTravelKeyword.from(user, travelKeyword));
        }

        userTravelKeywordRepository.saveAll(userTravelKeywords);

        return userTravelKeywords;
    }

    @Transactional
    public void sendMail(Long userId) {
        User user = getUserById(userId);
        SimpleMailMessage mailMessage = createMailMessage(user.getEmail());
        javaMailSender.send(mailMessage);
    }

    private SimpleMailMessage createMailMessage(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[Share Travel] 회원 탈퇴 인증 코드 발송");
        simpleMailMessage.setText("안녕하세요,\n귀하꼐서 요청하신 회원 탈퇴 처리를 위한 인증 코드를 다음과 같이 안내해드립니다.\n인증 코드 : " + createAuthorizationCode(email));

        return simpleMailMessage;
    }

   private int createAuthorizationCode(String email) {
       int authorizationCode = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
       redisTemplate.opsForValue().set(email, authorizationCode, 190, TimeUnit.SECONDS);
       return authorizationCode;
    }

    @Transactional
    public void deleteUSer(Long userId, Integer code) {
        User user = getUserById(userId);
        if (redisTemplate.opsForValue().get(user.getEmail()) == code) {
            userRepository.delete(user);
        } else {
            throw new IllegalStateException("회원 탈퇴 처리를 위한 인증 번호가 일치하지 않습니다.");
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
                });
    }
}
