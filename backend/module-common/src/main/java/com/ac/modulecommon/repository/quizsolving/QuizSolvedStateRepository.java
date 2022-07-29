package com.ac.modulecommon.repository.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizSolvedStateRepository extends JpaRepository<QuizSolvedState, Long>, QuizSolvedStateCustomRepository {
}
