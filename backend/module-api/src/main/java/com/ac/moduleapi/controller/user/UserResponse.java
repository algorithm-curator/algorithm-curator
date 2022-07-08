package com.ac.moduleapi.controller.user;

import com.ac.modulecommon.entity.user.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 API 관련 응답
 */
public class UserResponse {

    @Getter
    @Builder
    @JsonNaming(SnakeCaseStrategy.class)
    public static class JoinResponse {

        private Long id;
        private Long oauthId;

        public static JoinResponse from(User user) {
            return JoinResponse.builder()
                    .id(user.getId())
                    .oauthId(user.getOauthId())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateResponse {

        private Long id;

        public static UpdateResponse from(Long id) {
            return new UpdateResponse(id);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class NicknameResponse {

        private boolean result;

        public static NicknameResponse from(boolean result) {
            return new NicknameResponse(result);
        }
    }
}
