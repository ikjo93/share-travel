package com.sharetravel.global.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseCode {

    OAUTH2_LOGIN_SUCCESS("A01", 200, "로그인이 완료되었습니다."),
    OAUTH2_LOGIN_FAIL("A02", 401, "로그인이 실패했습니다."),
    TOKEN_INVALID("A03", 401, "유효하지 않은 토큰입니다."),
    TOKEN_HACKED("A04", 401, "토큰 도용이 의심됩니다."),
    TOKEN_REFRESHED("A05", 200, "액세스 토큰이 재발급되었습니다."),

    USER_NOT_FOUND("U01", 404, "존재하지 않는 회원입니다."),
    USER_UPDATE_SUCCESS("U02", 200, "회원 정보 수정이 완료되었습니다."),
    USER_DUPLICATE_NICKNAME("U03", 200, "이미 존재하는 닉네임입니다."),
    USER_NOT_DUPLICATE_NICKNAME("U04", 200, "사용가능한 닉네임입니다."),

    SERVER_INTERNAL_ERROR("G01", 500, "현재 서버 내부에서 에러가 발생했습니다.");

    private final String code;
    private final int httpStatusCode;
    private final String message;
}
