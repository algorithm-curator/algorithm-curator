package com.ac.modulecommon.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumApiException {

    NOT_FOUND("error.notfound", "error.notfound.details"),
    UNAUTHORIZED("error.authority","error.authority.details"),
    DUPLICATED_VALUE("error.duplicate","error.duplicate.details");

    private String messageKey;
    private String messageDetailKey;
}
