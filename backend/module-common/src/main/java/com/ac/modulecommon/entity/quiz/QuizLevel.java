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
    SILVER(0, "실버"),
    GOLD(1, "골드");

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
