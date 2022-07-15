package com.ac.modulecommon.repository.quizsolving.query;

import com.ac.modulecommon.entity.quiztype.QuizType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolvedCntByQuizTypeQueryDto {
    private QuizType quizType;
    private Long count;
}
