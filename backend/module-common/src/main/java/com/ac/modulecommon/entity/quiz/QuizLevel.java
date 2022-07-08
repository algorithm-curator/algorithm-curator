package com.ac.modulecommon.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 문제 난이도
 *
 * 실버, 골드, 레벨2, 레벨3...
 */
@Getter
@AllArgsConstructor
public enum QuizLevel {
    BOJ_SILVER(0, "실버"),
    BOJ_GOLD(1, "골드"),
    BOJ_PLATINUM(2, "플래티넘"),
    PG_LEVEL_ONE(100, "레벨1"),
    PG_LEVEL_TWO(101, "레벨2"),
    PG_LEVEL_THREE(102, "레벨3"),
    PG_LEVEL_FOUR(103, "레벨4"),
    PG_LEVEL_FIVE(104, "레벨5");

    private static final Map<Integer, QuizLevel> quizLevelMap =
            Stream.of(values()).collect(toMap(QuizLevel::getCode, value -> value));

    private Integer code;
    private String state;

    public static QuizLevel from(int quizLevelCode) {
        QuizLevel quizLevel = quizLevelMap.get(quizLevelCode);
        if (quizLevel == null) {
            throw new IllegalArgumentException("잘못된 QuizLevel 타입입니다. 0~2 중 하나를 입력해야 합니다.");
        }

        return quizLevel;
    }
}
