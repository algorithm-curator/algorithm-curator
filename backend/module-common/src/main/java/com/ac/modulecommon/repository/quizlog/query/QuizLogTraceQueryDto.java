package com.ac.modulecommon.repository.quizlog.query;

import com.ac.modulecommon.entity.quizsolving.SolvedState;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class QuizLogTraceQueryDto {
    private LocalDate date;
    private SolvedState solvedState;

    public QuizLogTraceQueryDto(String yyyymmdd, SolvedState solvedState) {
        this.date = LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.solvedState = solvedState;
    }
}
