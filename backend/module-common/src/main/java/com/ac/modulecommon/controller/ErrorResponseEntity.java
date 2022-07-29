package com.ac.modulecommon.controller;

import com.ac.modulecommon.exception.ApiException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import static com.ac.modulecommon.controller.ApiResult.ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseEntity {

    public static ResponseEntity<ApiResult<?>> from(Throwable throwable, boolean logFlag) {

        if (throwable.getCause() instanceof ApiException) {
            ApiException e = (ApiException) throwable.getCause();
            return new ResponseEntity<>(ERROR(e, e.getType().getStatus()), e.getType().getStatus());
        } else if (throwable.getCause() instanceof IllegalArgumentException) {
            return new ResponseEntity<>(ERROR(throwable.getCause(), BAD_REQUEST), BAD_REQUEST);
        } else if (throwable.getCause() instanceof IllegalStateException) {
            return new ResponseEntity<>(ERROR(throwable.getCause(), BAD_REQUEST), BAD_REQUEST);
        }

        if (logFlag) {
            log.warn("{}", throwable.getMessage(), throwable);
        }

        return new ResponseEntity<>(ERROR(throwable, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }
}
