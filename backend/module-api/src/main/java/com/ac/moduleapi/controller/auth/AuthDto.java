package com.ac.moduleapi.controller.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class AuthDto {

    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class LoginRequest {
        @NotBlank(message = "accessToken 값은 필수입니다.")
        private String accessToken;
    }

    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class LogoutRequest {
        @NotBlank(message = "accessToken 값은 필수입니다.")
        private String accessToken;
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class AuthResponse {
        //jwt
        private String apiToken;

        public static AuthResponse from(String apiToken) {
            return new AuthResponse(apiToken);
        }
    }
}
