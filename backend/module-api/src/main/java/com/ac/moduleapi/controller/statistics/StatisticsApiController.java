package com.ac.moduleapi.controller.statistics;

import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedCountResponse;
import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedRateResponse;
import com.ac.moduleapi.controller.statistics.StatisticsDto.SolvedTraceResponse;
import com.ac.moduleapi.service.quiz.QuizService;
import com.ac.moduleapi.service.quizlog.query.QuizLogQueryService;
import com.ac.moduleapi.service.quizlog.query.QuizLogTraceDto;
import com.ac.moduleapi.service.quizsolving.QuizSolvedStateService;
import com.ac.moduleapi.service.quizsolving.query.QuizSolvedStateQueryService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

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
        int solvingCount = quizSolvedStateService.getSolvedQuizSize(authentication.getId());
        Long totalCount = quizService.count();

        return OK(SolvedRateResponse.of(solvingCount, totalCount));
    }

    @GetMapping("/solved-trace")
    public ApiResult<SolvedTraceResponse> getSolvedTraces(@AuthenticationPrincipal JwtAuthentication authentication,
                                                          @RequestParam(value = "date", required = false) String yyyymmdd) {

        LocalDateTime firstUsageTime = quizLogQueryService.getFirstUsageTime(authentication.getId());

        if (isEmpty(yyyymmdd)) {
            List<QuizLogTraceDto> quizLogTraces = quizLogQueryService.getQuizLogTraces(authentication.getId(), LocalDate.now());
            return OK(SolvedTraceResponse.of(firstUsageTime, quizLogTraces));
        }

        LocalDate localDate = LocalDate.parse(yyyymmdd);
        List<QuizLogTraceDto> quizLogTraces = quizLogQueryService.getQuizLogTraces(authentication.getId(), localDate);
        return OK(SolvedTraceResponse.of(firstUsageTime, quizLogTraces));
    }
}
