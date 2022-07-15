package com.ac.moduleapi.controller.statistics;

import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedCountResponse;
import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedRateResponse;
import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedTraceResponse;
import com.ac.moduleapi.service.quiz.QuizService;
import com.ac.moduleapi.service.quizlog.query.QuizLogQueryService;
import com.ac.moduleapi.service.quizsolving.QuizSolvedStateService;
import com.ac.moduleapi.service.quizsolving.query.QuizSolvedStateQueryService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@RestController
public class StatisticsApiController {

    private final QuizSolvedStateService quizSolvedStateService;
    private final QuizService quizService;
    private final QuizSolvedStateQueryService quizSolvedStateQueryService;
    private final QuizLogQueryService quizLogQueryService;

    @GetMapping("/solved-count")
    public ApiResult<List<SolvedCountResponse>> getSolvedCountByQuizTypes(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(quizSolvedStateQueryService.getSolvedCountByQuizTypes(authentication.getId())
                .stream()
                .map(SolvedCountResponse::from)
                .collect(toList())
        );
    }

    @GetMapping("/solved-rate")
    public ApiResult<SolvedRateResponse> getSolvedRate(@AuthenticationPrincipal JwtAuthentication authentication) {
        int solvingCount = quizSolvedStateService.getSolvingCount(authentication.getId());
        Long totalCount = quizService.count();

        return OK(SolvedRateResponse.of(solvingCount, totalCount));
    }

    @GetMapping("/solved-trace")
    public ApiResult<List<SolvedTraceResponse>> getSolvedTraces(@AuthenticationPrincipal JwtAuthentication authentication) {
        return OK(quizLogQueryService.getQuizLogTraces(authentication.getId(), LocalDateTime.now())
                .stream()
                .map(SolvedTraceResponse::from)
                .collect(toList())
        );
    }
}
