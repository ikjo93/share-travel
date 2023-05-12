package com.sharetravel.global;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseMessage {

    private String code;
    private String message;

    public static ApiResponseMessage of(String code, String message) {
        return new ApiResponseMessage(code, message);
    }
}
