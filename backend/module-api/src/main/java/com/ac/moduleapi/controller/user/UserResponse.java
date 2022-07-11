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

        public static JoinResponse from(User user) {
            return JoinResponse.builder()
                    .id(user.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @JsonNaming(SnakeCaseStrategy.class)
    public static class GetResponse {

        private String nickname;
        private String profileImage;

        public static GetResponse from(User user) {
            return GetResponse.builder()
                    .nickname(user.getNickname())
                    .profileImage(null)
                    .build();
        }

        public static GetResponse of(User user, String profileUrl) {
            return GetResponse.builder()
                    .nickname(user.getNickname())
                    .profileImage(profileUrl)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateResponse {

        private String nickname;
        private String profileImage;

        public static UpdateResponse from(User user) {
            return new UpdateResponse(user.getNickname(), null);
        }

        public static UpdateResponse of(User user, String profileUrl) {
            return new UpdateResponse(user.getNickname(), profileUrl);
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
