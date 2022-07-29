package com.ac.modulecommon.jwt;

import com.ac.modulecommon.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * '인증된 사용자' 의미
 */
@Getter
@Builder
@RequiredArgsConstructor
public class JwtAuthentication {
    private final Long id;

    private final Long oauthId;

    private final String nickname;

    public static JwtAuthentication from(User user) {
        return JwtAuthentication.builder()
                .id(user.getId())
                .oauthId(user.getOauthId())
                .nickname(user.getNickname())
                .build();
    }
}
