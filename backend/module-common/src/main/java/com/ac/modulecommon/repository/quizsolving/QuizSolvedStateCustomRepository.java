package com.ac.modulecommon.repository.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizSolvedStateCustomRepository {
    List<QuizSolvedState> findAll(Long userId, SolvedState solvedState, Pageable pageable);
}
