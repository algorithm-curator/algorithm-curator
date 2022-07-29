package com.ac.modulecommon.repository.quizlog;

import com.ac.modulecommon.entity.quizlog.QuizLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizLogRepository extends JpaRepository<QuizLog, Long>, QuizLogCustomRepository {
}
