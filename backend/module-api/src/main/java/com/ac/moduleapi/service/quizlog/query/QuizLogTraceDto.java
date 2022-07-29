package com.ac.moduleapi.service.quizlog.query;

import com.ac.modulecommon.repository.quizlog.query.QuizLogTraceQueryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

import static com.ac.modulecommon.entity.quizsolving.SolvedState.SOLVED;

@Getter
@AllArgsConstructor
public class QuizLogTraceDto {
    private LocalDate date;
    private Integer state;

    public static QuizLogTraceDto from(QuizLogTraceQueryDto queryDto) {
        int state = queryDto.getSolvedState().equals(SOLVED) ? 1 : 0;
        return new QuizLogTraceDto(queryDto.getDate(), state);
    }
}
