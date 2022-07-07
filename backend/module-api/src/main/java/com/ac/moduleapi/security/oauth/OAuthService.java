package com.ac.moduleapi.security.oauth;

/**
 * 클라이언트로부터 OAuth accessToken을 받아 OAuth Server로부터 사용자 정보를 조회하는 역할
 */
public interface OAuthService {
    OAuthDto.LoginResponse getUserInfo(OAuthDto.LoginRequest request);

    OAuthDto.LogoutResponse logout(OAuthDto.LogoutRequest request);
}
