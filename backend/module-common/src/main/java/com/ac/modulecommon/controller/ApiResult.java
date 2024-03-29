package com.ac.modulecommon.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Map;

@ToString
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {

    private final boolean success;

    private final T response;

    private final ApiError error;

    // response body가 필요없는 status 200 응답
    public static <T> ApiResult<T> OK() { return new ApiResult<>(true, null, null); }

    public static <T> ApiResult<T> OK(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> NO_CONTENT() {
        return new ApiResult<>(true, null, null);
    }

    public static ApiResult<?> ERROR(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, ApiError.of(throwable, status));
    }

    public static ApiResult<?> ERROR(String errorMessage, HttpStatus status) {
        return new ApiResult<>(false, null, ApiError.of(errorMessage, status));
    }

    public static ApiResult<?> ERROR(String errorMessage, HttpStatus status, Map<String, String> invalidFields) {
        return new ApiResult<>(false, null, ApiError.of(errorMessage, status, invalidFields));
    }
}
