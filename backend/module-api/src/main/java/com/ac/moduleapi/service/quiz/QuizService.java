package com.ac.moduleapi.service.quiz;

import com.ac.modulecommon.entity.quiz.Quiz;

public interface QuizService {
    Quiz getQuiz(Long id);

    Long count();
}
