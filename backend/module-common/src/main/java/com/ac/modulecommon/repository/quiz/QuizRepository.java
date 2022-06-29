package com.ac.modulecommon.repository.quiz;

import com.ac.modulecommon.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
