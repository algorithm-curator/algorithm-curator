package com.ac.moduleapi.controller.ranking;

import com.ac.moduleapi.controller.ranking.RankingDto.GetResponse;
import com.ac.moduleapi.service.quizsolving.query.QuizSolvedStateQueryService;
import com.ac.modulecommon.controller.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RequestMapping("/api/rankings")
@RequiredArgsConstructor
@RestController
public class RankingApiController {

    private final QuizSolvedStateQueryService quizSolvedStateQueryService;

    @GetMapping
    public ApiResult<List<GetResponse>> getRanks(Pageable pageable) {
        return OK(quizSolvedStateQueryService.getRanks(pageable)
                .stream()
                .map(GetResponse::from)
                .collect(toList())
        );
    }
}
