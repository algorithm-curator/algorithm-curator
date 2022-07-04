package com.ac.moduleapi.controller.user;

import com.ac.moduleapi.security.oauth.OAuthDto;
import com.ac.moduleapi.security.oauth.OAuthService;
import com.ac.modulecommon.controller.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OAuthService oAuthService;

    //FIXME: 테스트용. 응답 스펙 바뀔 예정
    @PostMapping("/join")
    public ApiResult<OAuthDto.LoginResponse> join(@Valid @RequestBody UserRequest.JoinRequest request) {
        String accessToken = request.getAccessToken();
        OAuthDto.LoginResponse userInfo = oAuthService.getUserInfo(OAuthDto.LoginRequest.from(accessToken));
        return ApiResult.OK(userInfo);
    }

}
