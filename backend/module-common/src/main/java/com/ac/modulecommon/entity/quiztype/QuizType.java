package com.ac.modulecommon.entity.quiztype;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 알고리즘 유형
 *
 * DP, DFS, BFS 등
 */
@Getter
@AllArgsConstructor
public enum QuizType {
    DP(0, "Dynamic Programming"),
    DFS(1, "깊이우선탐색");

    private static final Map<Integer, QuizType> quizTypeMap =
            Stream.of(values()).collect(toMap(QuizType::getCode, value -> value));

    private Integer code;
    private String state;

    public static QuizType from(int quizTypeCode) {
        QuizType quizType = quizTypeMap.get(quizTypeCode);
        if (quizType == null) {
            throw new IllegalArgumentException("잘못된 QuizType 타입입니다. 0~2 중 하나를 입력해야 합니다.");
        }

        return quizType;
    }
}
