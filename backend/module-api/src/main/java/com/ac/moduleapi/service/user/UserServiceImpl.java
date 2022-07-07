package com.ac.moduleapi.service.user;

import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ac.modulecommon.exception.EnumApiException.NOT_FOUND;
import static com.google.common.base.Preconditions.checkArgument;

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
}