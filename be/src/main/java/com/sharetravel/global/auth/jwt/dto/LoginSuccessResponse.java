package com.sharetravel.global.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginSuccessResponse {

    private String name;
    private String email;
    private String nickName;
    private String picture;
    private String accessToken;

}
