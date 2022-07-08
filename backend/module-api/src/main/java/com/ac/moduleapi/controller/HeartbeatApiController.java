package com.ac.moduleapi.controller;

import com.ac.modulecommon.controller.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ac.modulecommon.controller.ApiResult.OK;

@RequestMapping("/api/heartbeat")
@RestController
public class HeartbeatApiController {

    @GetMapping
    public ApiResult<Long> getHeartbeat() {
        return OK(System.currentTimeMillis());
    }
}
