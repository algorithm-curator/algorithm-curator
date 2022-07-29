package com.ac.moduleapi.controller.user;

import com.ac.moduleapi.controller.user.UserRequest.JoinRequest;
import com.ac.moduleapi.controller.user.UserRequest.UpdateRequest;
import com.ac.moduleapi.controller.user.UserResponse.GetResponse;
import com.ac.moduleapi.controller.user.UserResponse.JoinResponse;
import com.ac.moduleapi.controller.user.UserResponse.NicknameResponse;
import com.ac.moduleapi.controller.user.UserResponse.UpdateResponse;
import com.ac.moduleapi.security.oauth.OAuthDto;
import com.ac.moduleapi.security.oauth.OAuthService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.controller.ErrorResponseEntity;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.jwt.JwtAuthentication;
import com.ac.modulecommon.util.PresignerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final OAuthService oAuthService;
    private final UserService userService;
    private final PresignerUtils presignerUtils;

    /**
     * return value: ApiResult<JoinResponse>
     */
    @PostMapping("/join")
    public CompletableFuture<ResponseEntity<ApiResult<?>>> join(@Valid @RequestBody JoinRequest request) {
        String accessToken = request.getAccessToken();
        OAuthDto.LoginResponse userInfo = oAuthService.getUserInfo(OAuthDto.LoginRequest.from(accessToken));

        return userService.create(userInfo.getId()).handle((userId, throwable) -> {
            if (userId != null) {
                User user = userService.getUser(userId);
                return new ResponseEntity<>(OK(JoinResponse.from(user)), HttpStatus.OK);
            }

            return ErrorResponseEntity.from(throwable, true);
        });
    }

    @GetMapping
    public ApiResult<GetResponse> getUser(@AuthenticationPrincipal JwtAuthentication authentication) {
        User user = userService.getUser(authentication.getId());

        if (isEmpty(user.getProfileImage())) {
            return OK(GetResponse.from(user));
        }

        String profilePresignedUrl = presignerUtils.getProfilePresignedGetUrl(user.getProfileImage());
        return OK(GetResponse.of(user, profilePresignedUrl));
    }

    @PutMapping
    public ApiResult<UpdateResponse> update(@AuthenticationPrincipal JwtAuthentication authentication,
                                            @Valid @RequestBody UpdateRequest request) {

        if (isEmpty(request.getProfileImage())) {
            long userId = userService.update(authentication.getId(), request.getNickname());
            User user = userService.getUser(userId);

            return OK(UpdateResponse.from(user));
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
