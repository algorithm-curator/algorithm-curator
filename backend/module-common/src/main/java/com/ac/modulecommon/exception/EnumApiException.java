package com.ac.modulecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EnumApiException {

    NOT_FOUND("error.notfound", "error.notfound.details", HttpStatus.NOT_FOUND),
    UNAUTHORIZED("error.authority","error.authority.details", HttpStatus.UNAUTHORIZED),
    DUPLICATED_VALUE("error.duplicate","error.duplicate.details", HttpStatus.BAD_REQUEST),
    ILLEGAL_ARGUMENT("error.illegal.argument","error.illegal.argument.details",HttpStatus.BAD_REQUEST);

    private String messageKey;
    private String messageDetailKey;
    private HttpStatus status;
}
