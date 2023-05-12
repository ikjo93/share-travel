package com.sharetravel.global.auth.jwt.exception;

public class HackedTokenException extends RuntimeException {

    public HackedTokenException() {
        super("토큰이 도용되었습니다.");
    }
}
