package com.ac.modulecommon.repository.user;

import com.ac.modulecommon.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
