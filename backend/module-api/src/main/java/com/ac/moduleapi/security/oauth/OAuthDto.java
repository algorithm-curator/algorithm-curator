package com.ac.moduleapi.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class OAuthDto {
    @Getter
    @AllArgsConstructor
    public static class LoginRequest {
        private String accessToken;

        public static OAuthDto.LoginRequest from(String accessToken) {
            return new OAuthDto.LoginRequest(accessToken);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class LogoutRequest {
        private String accessToken;

        public static OAuthDto.LogoutRequest from(String accessToken) {
            return new OAuthDto.LogoutRequest(accessToken);
        }
    }

    /**
     * OAuthService의 사용자 정보 응답으로부터 사용하고자 하는 값
     *
     * json response
     *
     * {
     *    "id": 1447910803,
     *    "connected_at": "2020-08-11T16:01:56Z"
     * }
     */
    @Getter
    public static class LoginResponse {
        private Long id;
    }

    @Getter
    public static class LogoutResponse {
        private Long id;
    }
}
