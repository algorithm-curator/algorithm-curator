package com.ac.moduleapi.controller.ranking;

import com.ac.modulecommon.repository.quizsolving.query.QuizSolvedStateRankQueryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

public class RankingDto {

    @Getter
    @Builder
    @JsonNaming(SnakeCaseStrategy.class)
    public static class GetResponse {
        private Long id;
        private String nickname;
        private Long solvedCount;

        public static GetResponse from(QuizSolvedStateRankQueryDto queryDto) {
            return GetResponse.builder()
                    .id(queryDto.getUserId())
                    .nickname(queryDto.getNickname())
                    .solvedCount(queryDto.getSolvedCount())
                    .build();
        }
    }
}
