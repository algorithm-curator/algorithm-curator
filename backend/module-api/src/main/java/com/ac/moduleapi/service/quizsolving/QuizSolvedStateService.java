package com.ac.moduleapi.service.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.repository.quiz.query.QuizQueryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuizSolvedStateService {
    /**
     * 문제를 랜덤으로 뽑을 수 있다.
     */
    List<QuizQueryDto> createRandomQuizzes(Long userId, int createCount);

    /**
     * insert
     * 보통 createRandomQuizzes 를 통해 반복호출되므로 동일한 사용자를 반복호출 하지않기 위해 userId 미사용
     */
    Long create(User user, Long quizId);

    /**
     * 내가 뽑은 문제 리스트 조회
     */
    List<QuizSolvedState> getQuizSolvedStates(Long userId, Pageable pageable);

    /**
     * 내가 뽑은 문제 리스트 조회
     */
    List<QuizSolvedState> getQuizSolvedStates(Long userId, SolvedState solvedState, Pageable pageable);

    QuizSolvedState getQuizSolvedState(Long id);

    //내가 현재 안 푼 문제 갯수 리턴
    int getUnsolvedQuizSize(Long userId);

    /**
     * 문제의 상태를 변경할 수 있다
     */
    void update(Long id, SolvedState solvedState);

    /**
     * 문제의 상태를 변경할 수 있다
     */
    void update(Long userId, List<Long> idList, SolvedState solvedState);

    /**
     * 해당 문제들을 '안 뽑음' 상태로 변경한다.
     */
    void delete(List<Long> idList);
}
