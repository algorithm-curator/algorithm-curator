package com.ac.modulelog.service;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;

public interface QuizLogService {
    Long createQuizLog(User user, Quiz quiz, SolvedState solvedState);
}
