package com.ac.modulecommon.repository.user;

public interface UserCustomRepository {
    boolean exists(String nickname);
    boolean existsByOAuth(Long oauthId);
}
