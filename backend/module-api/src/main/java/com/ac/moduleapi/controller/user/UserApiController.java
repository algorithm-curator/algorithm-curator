package com.ac.moduleapi.controller.user;

import com.ac.moduleapi.controller.user.UserRequest.JoinRequest;
import com.ac.moduleapi.controller.user.UserRequest.UpdateRequest;
import com.ac.moduleapi.controller.user.UserResponse.JoinResponse;
import com.ac.moduleapi.controller.user.UserResponse.NicknameResponse;
import com.ac.moduleapi.controller.user.UserResponse.UpdateResponse;
import com.ac.moduleapi.security.oauth.OAuthDto;
import com.ac.moduleapi.security.oauth.OAuthService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.jwt.JwtAuthentication;
import com.ac.modulecommon.util.PresignerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OAuthService oAuthService;
    private final UserService userService;
    private final PresignerUtils presignerUtils;

    @PostMapping("/join")
    public ApiResult<JoinResponse> join(@Valid @RequestBody JoinRequest request) {
        String accessToken = request.getAccessToken();
        OAuthDto.LoginResponse userInfo = oAuthService.getUserInfo(OAuthDto.LoginRequest.from(accessToken));

        Long userId = userService.create(userInfo.getId());
        User user = userService.getUser(userId);

        return OK(JoinResponse.from(user));
    }

    @PutMapping
    public ApiResult<UpdateResponse> update(@AuthenticationPrincipal JwtAuthentication authentication,
                                            @Valid @RequestBody UpdateRequest request) {

        if (isEmpty(request.getProfileImage())) {
            long userId = userService.update(authentication.getId(), request.getNickname());
            User user = userService.getUser(userId);

            if (isEmpty(user.getProfileImage())) {
                return OK(UpdateResponse.from(user));
            }

            String profilePresignedUrl = presignerUtils.getProfilePresignedGetUrl(user.getProfileImage());
            return OK(UpdateResponse.of(user, profilePresignedUrl));
        }

        long userId = userService.update(authentication.getId(), request.getNickname(), request.getProfileImage());
        User user = userService.getUser(userId);
        String profilePresignedUrl = presignerUtils.getProfilePresignedPutUrl(user.getProfileImage());
        return OK(UpdateResponse.of(user, profilePresignedUrl));
    }

    @GetMapping("/nickname")
    public ApiResult<NicknameResponse> isValidNickname(@RequestParam("name") String name) {
        return OK(NicknameResponse.from(userService.isUniqueNickname(name)));
    }
}
