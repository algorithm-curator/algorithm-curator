package com.ac.modulecommon.event;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * QuizSolvedState의 create, update, delete 이벤트를 포함하는 개념으로 change 사용
 */
@Getter
public class QuizSolvedStateChangeEvent {
    private final User user;
    private final Quiz quiz;
    private final SolvedState solvedState;

    public static QuizSolvedStateChangeEvent of(User user, Quiz quiz, SolvedState solvedState) {
        return new QuizSolvedStateChangeEvent(user, quiz, solvedState);
    }

    private QuizSolvedStateChangeEvent(User user, Quiz quiz, SolvedState solvedState) {
        checkArgument(user != null, "user 값은 필수입니다.");
        checkArgument(quiz != null, "quiz 값은 필수입니다.");
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");

        this.user = user;
        this.quiz = quiz;
        this.solvedState = solvedState;
    }
}
