package com.ac.moduleapi.controller.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * 사용자 API 관련 요청
 */
public class UserRequest {

    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class JoinRequest {
        @NotBlank(message = "accessToken 값은 필수입니다.")
        private String accessToken;
    }
}
