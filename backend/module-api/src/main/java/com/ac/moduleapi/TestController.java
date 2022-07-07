package com.ac.moduleapi;

import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.exception.EnumApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/v1")
    public void func1() {
        throw new ApiException(EnumApiException.NOT_FOUND, User.class, "hello", 1L, 1);
    }

    @GetMapping("/v2")
    public void func2() {
        throw new ApiException(EnumApiException.UNAUTHORIZED, "권한이 없습니다.");
    }

    @GetMapping("/v3")
    public void func3() {
        throw new ApiException(EnumApiException.DUPLICATED_VALUE, "중복된 값입니다.");
    }
}
