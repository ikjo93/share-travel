package com.sharetravel.domain.user.exception;

public class InvalidMailAuthorizationCodeException extends RuntimeException {

    public InvalidMailAuthorizationCodeException(String message) {
        super(message);
    }
}
