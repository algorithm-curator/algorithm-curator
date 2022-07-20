package com.ac.modulecommon.repository.quizsolving.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizSolvedStateRankQueryDto {
    private Long userId;
    private String nickname;
    private Long solvedCount;
}
