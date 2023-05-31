package com.sharetravel.domain.user.service;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

import com.sharetravel.domain.travelkeyword.entity.TravelKeyword;
import com.sharetravel.domain.travelkeyword.exception.NotFoundTravelKeywordException;
import com.sharetravel.domain.travelkeyword.repository.TravelKeywordRepository;
import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserInfoRequestDto;
import com.sharetravel.domain.user.entity.User;
import com.sharetravel.domain.user.entity.UserTravelKeyword;
import com.sharetravel.domain.user.exception.InvalidMailAuthorizationCodeException;
import com.sharetravel.domain.user.repository.MailAuthorizationCodeRepository;
import com.sharetravel.domain.user.repository.UserRepository;
import com.sharetravel.domain.user.repository.UserTravelKeywordRepository;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import com.sharetravel.global.auth.jwt.repository.RefreshTokenRepository;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
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
    private final MailAuthorizationCodeRepository mailAuthorizationCodeRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
        });
        return UserResponseDto.from(user);
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

        user.clearUserTravelKeyword();

        List<UserTravelKeyword> userTravelKeywords = createUserTravelKeywords(user, userInfo.getTravelKeywords());
        userTravelKeywordRepository.saveAll(userTravelKeywords);
        user.updateNickName(userInfo.getNickName());

        return UserResponseDto.from(user);
    }

    private List<UserTravelKeyword> createUserTravelKeywords(User user, List<Long> travelKeywordIds) {
        List<TravelKeyword> travelKeywords = travelKeywordRepository.findInIds(travelKeywordIds);
        if (travelKeywordIds.size() != travelKeywords.size()) {
            throw new NotFoundTravelKeywordException("존재하지 않는 여행지 키워드입니다.");
        }

        List<UserTravelKeyword> userTravelKeywords = new ArrayList<>();
        for (TravelKeyword travelKeyword : travelKeywords) {
            userTravelKeywords.add(UserTravelKeyword.createUserTravelKeyword(user, travelKeyword));
        }

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
        simpleMailMessage.setText("안녕하세요,\n\n귀하께서 요청하신 회원 탈퇴 처리를 위한 인증 코드를 다음과 같이 안내해드립니다.\n\n인증 코드 : " + createAuthorizationCode(email));

        return simpleMailMessage;
    }

   private int createAuthorizationCode(String email) {
       int authorizationCode = ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
       mailAuthorizationCodeRepository.setCodeByEmail(email, authorizationCode);
       return authorizationCode;
    }

    @Transactional
    public void deleteUSer(Long userId, String refreshTokenId, Integer code) {
        User user = getUserById(userId);
        if (mailAuthorizationCodeRepository.validateCode(user.getEmail(), code)) {
            userTravelKeywordRepository.deleteAllByIdInQuery(user.getIdsOfUserTravelKeyword());
            userRepository.deleteById(user.getId());
            refreshTokenRepository.deleteByKey(refreshTokenId);
        } else {
            throw new InvalidMailAuthorizationCodeException("회원 탈퇴 처리를 위한 인증 번호가 일치하지 않습니다.");
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalStateException("식별 번호가 " + id + "에 해당되는 사용자가 없습니다.");
                });
    }
}
