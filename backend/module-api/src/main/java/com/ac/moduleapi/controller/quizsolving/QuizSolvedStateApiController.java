package com.ac.moduleapi.controller.quizsolving;

import com.ac.moduleapi.controller.quizsolving.QuizSolvedStateDto.GetResponse;
import com.ac.moduleapi.controller.quizsolving.QuizSolvedStateDto.PickResponse;
import com.ac.moduleapi.controller.quizsolving.QuizSolvedStateDto.UpdateRequest;
import com.ac.moduleapi.service.quizsolving.QuizSolvedStateService;
import com.ac.modulecommon.controller.ApiResult;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.jwt.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ac.modulecommon.controller.ApiResult.OK;
import static java.util.stream.Collectors.toList;

@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@RestController
public class QuizSolvedStateApiController {

    private static final int MAX_UNSOLVED_COUNT = 10;
    private static final int PICK_COUNT = 3;

    private final QuizSolvedStateService quizSolvedStateService;

    @PostMapping
    public ApiResult<List<PickResponse>> pickQuizzes(@AuthenticationPrincipal JwtAuthentication authentication) {

        int unsolvedQuizSize = quizSolvedStateService.getUnsolvedQuizSize(authentication.getId());

        if (unsolvedQuizSize > MAX_UNSOLVED_COUNT) {
            throw new IllegalStateException("해결 못한 문제가 10문제를 초과하는 경우 더 이상 문제를 뽑을 수 없습니다.");
        }

        return OK(quizSolvedStateService.createRandomQuizzes(authentication.getId(), PICK_COUNT)
                    .stream()
                    .map(PickResponse::from)
                    .collect(toList()));
    }

    /**
     * 문제 목록 조회
     */
    @GetMapping
    public ApiResult<List<GetResponse>> getQuizzes(@AuthenticationPrincipal JwtAuthentication authentication,
                                                   @RequestParam(value = "state", required = false) Integer solvedState,
                                                   Pageable pageable) {

        if (solvedState == null) {
            return OK(quizSolvedStateService.getQuizSolvedStates(authentication.getId(), pageable)
                    .stream()
                    .map(GetResponse::from)
                    .collect(toList()));
        }

        return OK(quizSolvedStateService.getQuizSolvedStates(authentication.getId(),
                                                             SolvedState.from(solvedState),
                                                             pageable)
                .stream()
                .map(GetResponse::from)
                .collect(toList()));
    }

    /**
     * 문제 상태 변경
     */
    @PutMapping
    public ApiResult<Void> updateQuizzes(@AuthenticationPrincipal JwtAuthentication authentication,
                                      @Valid @RequestBody UpdateRequest request) {

        quizSolvedStateService.update(authentication.getId(),
                                        request.getIdList(),
                                        SolvedState.from(request.getState()));

        return OK();
    }
}
