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
    DFS(1, "깊이우선탐색"),
    BFS(2,"너비우선탐색"),
    BINARY_SEARCH(3,"이분탐색"),
    GRAPH(4,"그래프"),
    BRUTE_FORCE(5,"완전탐색"),
    GREEDY(6,"그리디"),
    DS(7,"자료구조"),
    SIMULATION(8,"시뮬레이션"),
    BACKTRACKING(9,"백트래킹"),
    BIT_MASKING(10,"비트마스킹"),
    MATH(11,"수학"),
    PREFIX_SUM(12,"누적합"),
    DIJKSTRA(13,"다익스트라"),
    FLOYD(14,"플로이드 와샬"),
    TOPOLOGICAL_SORT(15,"위상 정렬"),
    SORT(16,"정렬"),
    STRING(17,"문자열");

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
