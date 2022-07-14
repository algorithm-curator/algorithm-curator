package com.ac.moduleapi.controller.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.repository.quiz.query.QuizQueryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class QuizSolvedStateDto {

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class PickResponse {

        private Long id;
        private String title;
        private String quizUrl;
        private String quizLevel;
        private String quizPlatform;

        public static PickResponse from(QuizQueryDto quizQueryDto) {
            return PickResponse.builder()
                    .id(quizQueryDto.getQuizSolvedStateId())
                    .title(quizQueryDto.getTitle())
                    .quizUrl(quizQueryDto.getQuizUrl())
                    .quizLevel(quizQueryDto.getQuizLevel().getState())
                    .quizPlatform(quizQueryDto.getQuizPlatform().getState())
                    .build();
        }
    }

    @Builder
    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class GetResponse {

        private Long id;
        private String title;
        private String quizUrl;
        private String quizLevel;
        private String quizPlatform;

        public static GetResponse from(QuizSolvedState quizSolvedState) {
            return GetResponse.builder()
                    .id(quizSolvedState.getId())
                    .title(quizSolvedState.getQuiz().getTitle())
                    .quizUrl(quizSolvedState.getQuiz().getQuizUrl())
                    .quizLevel(quizSolvedState.getQuiz().getLevel().getState())
                    .quizPlatform(quizSolvedState.getQuiz().getPlatform().getState())
                    .build();
        }
    }

    @Getter
    @JsonNaming(SnakeCaseStrategy.class)
    public static class UpdateRequest {
        @NotNull(message = "idList 값은 필수입니다.")
        @Size(max = 10)
        private List<QuizUpdateDto> problems;

        @Getter
        @JsonNaming(SnakeCaseStrategy.class)
        public static class QuizUpdateDto {

            @NotNull(message = "id 값은 필수입니다.")
            private Long id;

            @Min(value = 1)
            @Max(value = 2)
            @NotNull(message = "state 값은 필수입니다.")
            private Integer state;
        }
    }
}
