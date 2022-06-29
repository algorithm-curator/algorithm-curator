package com.ac.modulecommon.repository.quiz.query;

import com.ac.modulecommon.entity.quiz.QuizLevel;
import com.ac.modulecommon.entity.quiz.QuizPlatform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 문제를 랜덤으로 뽑는 로직을 위해 사용하는 Dto
 */
@ToString
@Getter
@Builder
@AllArgsConstructor
public class QuizQueryDto {
    private Long quizSolvedStateId;
    private Long quizId;
    private String title;
    private String quizUrl;
    private QuizLevel quizLevel;
    private QuizPlatform quizPlatform;

    public static QuizQueryDto of(Long quizSolvedStateId, QuizQueryDto dto) {
        return QuizQueryDto.builder()
                .quizSolvedStateId(quizSolvedStateId)
                .quizId(dto.getQuizId())
                .title(dto.getTitle())
                .quizUrl(dto.getQuizUrl())
                .quizLevel(dto.getQuizLevel())
                .quizPlatform(dto.getQuizPlatform())
                .build();
    }
}
