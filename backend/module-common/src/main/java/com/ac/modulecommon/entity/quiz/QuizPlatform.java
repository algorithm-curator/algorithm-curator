package com.ac.modulecommon.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * 알고리즘 문제집 플랫폼
 *
 * 백준, 프로그래머스
 */
@Getter
@AllArgsConstructor
public enum QuizPlatform {
    BOJ(0, "백준"),
    PG(1, "프로그래머스");

    private static final Map<Integer, QuizPlatform> quizPlatformMap =
            Stream.of(values()).collect(toMap(QuizPlatform::getCode, value -> value));

    private Integer code;
    private String state;

    public static QuizPlatform from(int quizPlatformCode) {
        QuizPlatform quizPlatform = quizPlatformMap.get(quizPlatformCode);
        if (quizPlatform == null) {
            throw new IllegalArgumentException("잘못된 QuizPlatform 타입입니다. 0~1 중 하나를 입력해야 합니다.");
        }

        return quizPlatform;
    }
}
