package com.ac.modulecommon.entity.user;

import com.ac.modulecommon.exception.ApiException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import static com.ac.modulecommon.exception.EnumApiException.ILLEGAL_ARGUMENT;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Role {

    USER("ROLE_USER");

    private final String value;

    public String value() {
        return value;
    }

    public static Role from(String name) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }

        throw new ApiException(ILLEGAL_ARGUMENT, String.format("%s는 올바르지 않은 Role입니다.", name));
    }
}
