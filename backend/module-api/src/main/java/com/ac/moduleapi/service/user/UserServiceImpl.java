package com.ac.moduleapi.service.user;

import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkArgument;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        checkArgument(id != null, "id 값은 필수입니다.");
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("FIXME"));
    }
}
