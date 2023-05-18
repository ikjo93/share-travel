package com.sharetravel.domain.user.controller;

import com.sharetravel.domain.user.dto.UserResponseDto;
import com.sharetravel.domain.user.dto.UserUpdateRequestDto;
import com.sharetravel.domain.user.service.UserService;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.sharetravel.global.api.ApiUtil.getResponseEntity;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public UserResponseDto find(@AuthenticationPrincipal Long userId) {
        return userService.findById(userId);
    }

    @PatchMapping("/api/users")
    public UserResponseDto update(@AuthenticationPrincipal Long userId, @Valid @RequestBody UserUpdateRequestDto request) {
        return userService.update(userId, request);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseMessage> handleInvalidTokenException() {
        return getResponseEntity(ApiResponseCode.USER_NOT_FOUND);
    }

    // TODO : 사용자 닉네임이 등록되지 않은 경우 클라이언트는 사용자가 닉네임을 등록하도록 하고 서버는 클라이언트로부터 사용자 닉네임을 받아 등록
    // TODO : 회원 탈퇴 기능(구글 SMTP)
}
