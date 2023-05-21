package com.sharetravel.domain.user.controller;

import com.sharetravel.domain.travelkeyword.exception.NotFoundTravelKeywordException;
import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserInfoRequestDto;
import com.sharetravel.domain.user.exception.InvalidMailAuthorizationCodeException;
import com.sharetravel.domain.user.service.UserService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import com.sharetravel.global.auth.jwt.argumentresolver.RefreshTokenId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

@RequiredArgsConstructor
@RestController
public class UserController {

    private static final int NICKNAME_MIN_LENGTH = 6;
    private static final int NICKNAME_MAX_LENGTH = 15;
    private static final int MAIL_AUTHORIZATION_CODE_LENGTH = 15;

    private final UserService userService;

    @GetMapping("/api/users")
    public UserResponseDto find(@AuthenticationPrincipal Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/api/users/duplicate")
    public ResponseEntity<ApiResponseMessage> validate(@RequestParam @Min(NICKNAME_MIN_LENGTH) @Max(NICKNAME_MAX_LENGTH) String nickName) {
        return userService.validateDuplicate(nickName);
    }

    @PostMapping("/api/users")
    public UserResponseDto register(@AuthenticationPrincipal Long userId, @Valid @RequestBody UserInfoRequestDto userInfo) {
        return userService.register(userId, userInfo);
    }

    @PostMapping("/api/users/mail")
    public ResponseEntity<ApiResponseMessage> mail(@AuthenticationPrincipal Long userId) {
        userService.sendMail(userId);
        return getResponseEntity(ApiResponseCode.USER_EMAIL_SENDING_SUCCESS);
    }

    @DeleteMapping("/api/users")
    public ResponseEntity<ApiResponseMessage> delete(@AuthenticationPrincipal Long userId,
        @RefreshTokenId String refreshTokenId,
        @RequestParam @Length(min = MAIL_AUTHORIZATION_CODE_LENGTH, max = MAIL_AUTHORIZATION_CODE_LENGTH) Integer code) {
        userService.deleteUSer(userId, refreshTokenId, code);
        return getResponseEntity(ApiResponseCode.USER_EMAIL_SENDING_SUCCESS);
    }

    @ExceptionHandler(NotFoundTravelKeywordException.class)
    public ResponseEntity<ApiResponseMessage> handleNotFoundTravelKeywordException() {
        return getResponseEntity(ApiResponseCode.TRAVEL_KEYWORD_NOT_FOUND);
    }

    @ExceptionHandler(InvalidMailAuthorizationCodeException.class)
    public ResponseEntity<ApiResponseMessage> handleInvalidMailAuthorizationCodeException() {
        return getResponseEntity(ApiResponseCode.USER_EMAIL_SENDING_SUCCESS);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseMessage> handleUserNotFoundException() {
        return getResponseEntity(ApiResponseCode.USER_NOT_FOUND);
    }
}
