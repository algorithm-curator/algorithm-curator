package com.ac.moduleapi.controller.statistics;

import com.ac.moduleapi.service.quizlog.query.QuizLogTraceDto;
import com.ac.modulecommon.repository.quizsolving.query.SolvedCntByQuizTypeQueryDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class StatisticsDto {

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class SolvedCountResponse {
        private String type;
        private Long count;

        public static SolvedCountResponse from(SolvedCntByQuizTypeQueryDto queryDto) {
            return new SolvedCountResponse(queryDto.getQuizType().getState(), queryDto.getCount());
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class SolvedRateResponse {
        private Integer solvedCount;
        private Long totalCount;

        public static SolvedRateResponse of(int solvedCount, Long totalCount) {
            return new SolvedRateResponse(solvedCount, totalCount);
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonNaming(SnakeCaseStrategy.class)
    public static class SolvedTraceResponse {
        private LocalDate date;
        private Integer state;

        public static SolvedTraceResponse from(QuizLogTraceDto traceDto) {
            return new SolvedTraceResponse(traceDto.getDate(), traceDto.getState());
        }
    }
}
