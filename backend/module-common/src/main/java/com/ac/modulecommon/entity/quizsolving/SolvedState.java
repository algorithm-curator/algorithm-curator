package com.ac.modulecommon.entity.quizsolving;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
@AllArgsConstructor
public enum SolvedState {
    NOT_PICKED(0, "안뽑음"),
    UNSOLVED(1, "풀이 미완료"),
    SOLVED(2, "풀이 완료");

    private static final Map<Integer, SolvedState> solvedStateMap =
            Stream.of(values()).collect(toMap(SolvedState::getCode, value -> value));

    private Integer code;
    private String state;

    public static SolvedState from(int solvedStateCode) {
        SolvedState productState = solvedStateMap.get(solvedStateCode);
        if (productState == null) {
            throw new IllegalArgumentException("잘못된 SolvedState 타입입니다. 0~2 중 하나를 입력해야 합니다.");
        }

        return productState;
    }
}
