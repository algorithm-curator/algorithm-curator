package com.ac.modulecommon.repository.user;

import com.ac.modulecommon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
    Optional<User> findById(Long id);

    Optional<User> findByOauthId(Long oauthId);
}
