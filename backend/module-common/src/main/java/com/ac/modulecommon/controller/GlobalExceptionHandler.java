package com.ac.modulecommon.controller;

import com.ac.modulecommon.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartException;

import java.util.Map;

import static com.ac.modulecommon.controller.ApiResult.ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiResult<?>> createResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }

    private ResponseEntity<ApiResult<?>> createResponseByInvalidFields(String errorMessage,
                                                                       HttpStatus status,
                                                                       Map<String, String> invalidFields) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(errorMessage, status, invalidFields), headers, status);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class, MultipartException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        return createResponse(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * handle kakao login api exception
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getRawStatusCode());
        return createResponse(e, httpStatus);
    }

    /**
     * api 모듈에서 정의한 비즈니스 예외가 발생하는 경우
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        return createResponse(e, e.getType().getStatus());
    }

    /**
     * 예상하지 못한 API Server Exception Handling
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
