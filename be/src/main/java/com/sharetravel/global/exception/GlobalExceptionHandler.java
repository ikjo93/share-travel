package com.sharetravel.global.exception;

import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseMessage> handleMethodArgumentTypeMismatchException() {
        ApiResponseCode code = ApiResponseCode.SERVER_INTERNAL_ERROR;
        return ResponseEntity.status(code.getHttpStatusCode())
                .body(ApiResponseMessage.of(code.getCode(), code.getMessage()));
    }
}
