package com.ac.moduleapi.service.quizsolving;

import com.ac.moduleapi.service.quiz.QuizService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.event.QuizSolvedStateChangeEvent;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quiz.query.QuizQueryDto;
import com.ac.modulecommon.repository.quiz.query.QuizQueryRepository;
import com.ac.modulecommon.repository.quizsolving.QuizSolvedStateRepository;
import com.google.common.eventbus.EventBus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.ac.modulecommon.entity.quizsolving.SolvedState.*;
import static com.ac.modulecommon.exception.EnumApiException.NOT_FOUND;
import static com.ac.modulecommon.exception.EnumApiException.UNAUTHORIZED;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizSolvedStateServiceImpl implements QuizSolvedStateService {

    private static final int QUIZ_SEARCH_SIZE = 100;

    private final QuizSolvedStateRepository quizSolvedStateRepository;
    private final QuizQueryRepository quizQueryRepository;
    private final QuizService quizService;
    private final UserService userService;
    private final EventBus eventBus;

    @Async
    @Override
    @Transactional
    public CompletableFuture<List<QuizQueryDto>> createRandomQuizzes(Long userId, int createCount) {
        checkArgument(userId != null, "userId 값은 필수입니다.");
        checkArgument(0 < createCount && createCount <= 3, "count 값은 1~3 입니다.");

        List<QuizQueryDto> candidates = quizQueryRepository.findAll(userId, PageRequest.of(0, QUIZ_SEARCH_SIZE));
        List<QuizQueryDto> modifiableCandidates = new ArrayList<>(candidates);

        Collections.shuffle(modifiableCandidates);

        User user = userService.getUser(userId);

        List<QuizQueryDto> randomQuizzes = modifiableCandidates.subList(0, createCount);

        return completedFuture(randomQuizzes.stream().map(quiz -> {
            if (quiz.getQuizSolvedStateId() == null) {
                Long quizSolvedStateId = create(user, quiz.getQuizId());
                return QuizQueryDto.of(quizSolvedStateId, quiz);
            } else {
                update(quiz.getQuizSolvedStateId(), UNSOLVED, user.getId());
                return quiz;
            }
        }).collect(toList()));
    }

    @Override
    @Transactional
    public Long create(User user, Long quizId) {
        checkArgument(user != null, "user 값은 필수입니다.");
        checkArgument(quizId != null, "quizId 값은 필수입니다.");

        Quiz quiz = quizService.getQuiz(quizId);

        QuizSolvedState state = QuizSolvedState.builder()
                                                .user(user)
                                                .quiz(quiz)
                                                .solvedState(UNSOLVED)
                                                .build();

        QuizSolvedState savedState = quizSolvedStateRepository.save(state);
        eventBus.post(QuizSolvedStateChangeEvent.of(savedState.getUser(), savedState.getQuiz(), UNSOLVED));

        return savedState.getId();
    }

    @Override
    public List<QuizSolvedState> getQuizSolvedStates(Long userId, Pageable pageable) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        return quizSolvedStateRepository.findAll(userId, null, pageable);
    }

    @Override
    public List<QuizSolvedState> getQuizSolvedStates(Long userId, SolvedState solvedState, Pageable pageable) {
        checkArgument(userId != null, "userId 값은 필수입니다.");
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");
        checkArgument(!solvedState.equals(NOT_PICKED), "solvedState는 풀이 미완료/완료 값이어야 합니다.");

        return quizSolvedStateRepository.findAll(userId, solvedState, pageable);
    }

    @Override
    public QuizSolvedState getQuizSolvedState(Long id) {
        checkArgument(id != null, "id 값은 필수입니다.");

        return quizSolvedStateRepository.findOne(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND,
                                                    QuizSolvedState.class,
                                                    String.format("id = %s", id)));
    }

    @Override
    public int getUnsolvedQuizSize(Long userId) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        return quizSolvedStateRepository.countBySolvedState(userId, UNSOLVED);
    }

    @Override
    public int getSolvedQuizSize(Long userId) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        return quizSolvedStateRepository.countBySolvedState(userId, SOLVED);
    }

    @Override
    @Transactional
    public void update(Long id, SolvedState solvedState, Long userId) {
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");
        checkArgument(!solvedState.equals(NOT_PICKED), "안뽑음 상태로는 변경할 수 없습니다. 삭제 API를 이용하세요.");
        checkArgument(userId != null, "userId 값은 필수입니다.");

        QuizSolvedState quizSolvedState = getQuizSolvedState(id);

        if (!isOwner(quizSolvedState, userId)) {
            throw new ApiException(UNAUTHORIZED, "본인이 뽑은 문제만 상태를 변경할 수 있습니다.");
        }

        quizSolvedState.update(solvedState);
        eventBus.post(QuizSolvedStateChangeEvent.of(quizSolvedState.getUser(), quizSolvedState.getQuiz(), solvedState));
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        QuizSolvedState quizSolvedState = getQuizSolvedState(id);

        if (!isOwner(quizSolvedState, userId)) {
            throw new ApiException(UNAUTHORIZED, "본인이 뽑은 문제만 삭제할 수 있습니다.");
        }

        quizSolvedState.update(NOT_PICKED);
        eventBus.post(QuizSolvedStateChangeEvent.of(quizSolvedState.getUser(), quizSolvedState.getQuiz(), NOT_PICKED));
    }

    private boolean isOwner(QuizSolvedState quizSolvedState, Long userId) {
        return quizSolvedState.getUser().getId().equals(userId);
    }
}
