package com.ac.modulecommon.repository.quizlog;

import com.ac.modulecommon.entity.quizlog.QuizLog;

import java.util.Optional;

public interface QuizLogCustomRepository {
    Optional<QuizLog> findFirstByUserId(Long userId);
}
