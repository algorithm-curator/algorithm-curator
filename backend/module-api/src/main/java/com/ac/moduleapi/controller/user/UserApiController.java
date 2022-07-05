package com.ac.moduleapi.controller.user;

import com.ac.moduleapi.security.oauth.OAuthDto;
import com.ac.moduleapi.security.oauth.OAuthService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ac.modulecommon.controller.ApiResult.OK;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OAuthService oAuthService;
    private final UserService userService;

    @PostMapping("/join")
    public ApiResult<UserResponse.JoinResponse> join(@Valid @RequestBody UserRequest.JoinRequest request) {
        String accessToken = request.getAccessToken();
        OAuthDto.LoginResponse userInfo = oAuthService.getUserInfo(OAuthDto.LoginRequest.from(accessToken));

        Long userId = userService.create(userInfo.getId());
        User user = userService.getUser(userId);

        return OK(UserResponse.JoinResponse.from(user));
    }

}
