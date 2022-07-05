package com.ac.moduleapi.service.user;

import com.ac.modulecommon.entity.user.User;

public interface UserService {
    Long create(Long oauthId);
    User getUser(Long id);
    User getUserByOauthId(Long oauthId);
}
