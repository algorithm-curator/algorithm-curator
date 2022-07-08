package com.ac.moduleapi.service.user;

import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ac.modulecommon.exception.EnumApiException.NOT_FOUND;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long create(Long oauthId) {
        checkArgument(oauthId != null, "oauthId 값은 필수입니다.");

        if (userRepository.existsByOAuth(oauthId)) {
            return getUserByOauthId(oauthId).getId();
        }

        User user = User.builder()
                        .oauthId(oauthId)
                        .build();

        return userRepository.save(user).getId();
    }

    @Override
    public User getUser(Long id) {
        checkArgument(id != null, "id 값은 필수입니다.");

        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND,
                                                    User.class,
                                                    String.format("id = %s", id)));
    }

    @Override
    public User getUserByOauthId(Long oauthId) {
        checkArgument(oauthId != null, "oauthId 값은 필수입니다.");

        return userRepository.findByOauthId(oauthId)
                                .orElseThrow(() -> new ApiException(NOT_FOUND,
                                                                    User.class,
                                                                    String.format("oauthId = %s", oauthId)));
    }

    @Override
    @Transactional
    public long update(Long id, String nickname) {
        checkArgument(isNotBlank(nickname), "nickname 값은 필수입니다.");

        User user = getUser(id);
        user.update(nickname);
        return user.getId();
    }

    @Override
    @Transactional
    public long update(Long id, String nickname, String profileImage) {
        checkArgument(isNotBlank(nickname), "nickname 값은 필수입니다.");
        checkArgument(isNotBlank(profileImage), "profileImage 값은 필수입니다.");

        User user = getUser(id);
        user.update(nickname, profileImage);
        return user.getId();
    }

    @Override
    public boolean isUniqueNickname(String nickname) {
        checkArgument(isNotBlank(nickname), "nickname 값은 필수입니다.");
        checkArgument(nickname.length() <= 20, "nickname 길이는 20자 이하만 가능합니다.");

        return !userRepository.exists(nickname);
    }
}
