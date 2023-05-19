package com.sharetravel.domain.user.controller;

import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserInfoRequestDto;
import com.sharetravel.domain.user.service.UserService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public UserResponseDto find(@AuthenticationPrincipal Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/api/users/duplicate")
    public ResponseEntity<ApiResponseMessage> validate(@RequestParam @Min(6) @Max(15) String nickName) {
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
    public ResponseEntity<ApiResponseMessage> delete(@AuthenticationPrincipal Long userId, @RequestParam Integer code) {
        userService.deleteUSer(userId, code);
        return getResponseEntity(ApiResponseCode.USER_EMAIL_SENDING_SUCCESS);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseMessage> handleInvalidTokenException() {
        return getResponseEntity(ApiResponseCode.USER_NOT_FOUND);
    }
}
