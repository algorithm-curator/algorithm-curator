package com.ac.moduleapi.security.oauth;

import com.ac.moduleapi.config.OAuthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 클라이언트로부터 OAuth accessToken을 받아 OAuth Server로부터 사용자 정보를 조회하는 클래스
 *
 * 구현체인 KakaoOAuthService는 카카오 API 스펙을 따른다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoOAuthService implements OAuthService {

    private final RestTemplate restTemplate;

    private final OAuthConfig oAuthConfig;

    @Override
    public OAuthDto.LoginResponse getUserInfo(OAuthDto.LoginRequest request) {
        HttpEntity authRequest = createLoginRequest(request);
        String requestUrl = oAuthConfig.getLoginUrl();

        ResponseEntity<OAuthDto.LoginResponse> oauthResponse
                = restTemplate.exchange(requestUrl, HttpMethod.GET, authRequest, OAuthDto.LoginResponse.class);

        log.info("authentication response body = {}", oauthResponse.getBody());

        return oauthResponse.getBody();
    }

    @Override
    public OAuthDto.LogoutResponse logout(OAuthDto.LogoutRequest request) {
        HttpEntity logoutRequest = createLogoutRequest(request);
        String requestUrl = oAuthConfig.getLogoutUrl();

        ResponseEntity<OAuthDto.LogoutResponse> oauthResponse
                = restTemplate.exchange(requestUrl, HttpMethod.POST, logoutRequest, OAuthDto.LogoutResponse.class);

        log.info("logout response body = {}", oauthResponse.getBody());

        return oauthResponse.getBody();
    }

    /**
     * HttpHeader key: Authorization
     * HttpHeader value: Bearer ${ACCESS_TOKEN}
     *
     * @return Authorization: Bearer ${ACCESS_TOKEN}
     */
    private HttpEntity createLoginRequest(OAuthDto.LoginRequest loginRequest) {
        String headerValue = createRequestHeader(loginRequest.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.set(oAuthConfig.getHeaderKey(), headerValue);

        return new HttpEntity(headers);
    }

    /**
     * HttpHeader key: Authorization
     * HttpHeader value: Bearer ${ACCESS_TOKEN}
     *
     * @return Authorization: Bearer ${ACCESS_TOKEN}
     */
    private HttpEntity createLogoutRequest(OAuthDto.LogoutRequest logoutRequest) {
        String headerValue = createRequestHeader(logoutRequest.getAccessToken());

        HttpHeaders headers = new HttpHeaders();
        headers.set(oAuthConfig.getHeaderKey(), headerValue);

        return new HttpEntity(headers);
    }

    /**
     * @return Bearer ${ACCESS_TOKEN}
     */
    private String createRequestHeader(String accessToken) {
        return String.format("%s %s", oAuthConfig.getHeaderValue(), accessToken);
    }
}
