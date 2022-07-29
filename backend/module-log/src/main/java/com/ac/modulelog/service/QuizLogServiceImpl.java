package com.ac.modulelog.service;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizlog.QuizLog;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.repository.quizlog.QuizLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkArgument;

@Transactional
@RequiredArgsConstructor
@Service
public class QuizLogServiceImpl implements QuizLogService {

    private final QuizLogRepository quizLogRepository;

    @Override
    public Long createQuizLog(User user, Quiz quiz, SolvedState solvedState) {
        checkArgument(user != null, "user 값은 필수입니다.");
        checkArgument(quiz != null, "quiz 값은 필수입니다.");
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");

        QuizLog quizLog = QuizLog.builder()
                .user(user)
                .quiz(quiz)
                .solvedState(solvedState)
                .build();

        return quizLogRepository.save(quizLog).getId();
    }
}
