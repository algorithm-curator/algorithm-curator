package com.ac.modulecommon.repository.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuizSolvedStateCustomRepository {
    Optional<QuizSolvedState> findOne(Long id);
    List<QuizSolvedState> findAll(Long userId, SolvedState solvedState, Pageable pageable);
    int countAllBySolvedState(Long userId, SolvedState solvedState);
}
