package com.ac.moduleapi.controller.auth;

import com.ac.modulecommon.entity.user.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
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
    @Builder
    @JsonNaming(SnakeCaseStrategy.class)
    public static class AuthResponse {
        //jwt
        private String apiToken;

        //userInfo

        public static AuthResponse of(String apiToken, User user) {
            return AuthResponse.builder()
                    .apiToken(apiToken)
                    .build();
        }
    }
}
