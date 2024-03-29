package com.ac.modulecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EnumApiException {
    NO_CONTENT("response.no-content", "response.no-content.details", HttpStatus.NO_CONTENT),
    DUPLICATED_VALUE("error.duplicate","error.duplicate.details", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("error.authority","error.authority.details", HttpStatus.FORBIDDEN),
    NOT_FOUND("error.notfound", "error.notfound.details", HttpStatus.NOT_FOUND);

    private String messageKey;
    private String messageDetailKey;
    private HttpStatus status;
}
