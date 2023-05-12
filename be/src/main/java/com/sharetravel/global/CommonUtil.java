package com.sharetravel.global;

import org.springframework.http.ResponseEntity;

public class CommonUtil {

    public static ResponseEntity<ApiResponseMessage> getResponseEntity(ApiResponseCode responseCode) {
        return ResponseEntity.status(responseCode.getHttpStatusCode())
            .body(
                ApiResponseMessage.of(responseCode.getCode(), responseCode.getMessage())
            );
    }

}
