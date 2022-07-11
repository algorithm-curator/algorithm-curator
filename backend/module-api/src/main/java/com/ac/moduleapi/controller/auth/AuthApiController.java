package com.ac.moduleapi.controller.auth;

import com.ac.moduleapi.security.oauth.OAuthDto;
import com.ac.moduleapi.security.oauth.OAuthService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ac.modulecommon.controller.ApiResult.OK;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final OAuthService oAuthService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResult<AuthDto.AuthResponse> login(@Valid @RequestBody AuthDto.LoginRequest loginRequest) {

        String accessToken = loginRequest.getAccessToken();

        //user에 대해 AuthenticationManager 동작
        OAuthDto.LoginResponse oauthResponse = oAuthService.getUserInfo(OAuthDto.LoginRequest.from(accessToken));

        JwtAuthenticationToken authToken = JwtAuthenticationToken.from(oauthResponse.getId());
        Authentication authenticate = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        AuthDto.AuthResponse response = (AuthDto.AuthResponse)authenticate.getDetails();

        //TODO: presignedUrl 적용해서 return 하기

        return OK(response);
    }

    @PostMapping("/logout")
    public ApiResult<Void> logout(@Valid @RequestBody AuthDto.LogoutRequest logoutRequest) {

        String accessToken = logoutRequest.getAccessToken();

        oAuthService.logout(OAuthDto.LogoutRequest.from(accessToken));
        SecurityContextHolder.clearContext();

        return OK();
    }
}
