package com.ac.modulecommon.message;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.event.QuizSolvedStateChangeEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizSolvedStateChangeMessage {
    private User user;
    private Quiz quiz;
    private SolvedState solvedState;

    public static QuizSolvedStateChangeMessage from(QuizSolvedStateChangeEvent event) {
        return QuizSolvedStateChangeMessage.builder()
                .user(event.getUser())
                .quiz(event.getQuiz())
                .solvedState(event.getSolvedState())
                .build();
    }
}
