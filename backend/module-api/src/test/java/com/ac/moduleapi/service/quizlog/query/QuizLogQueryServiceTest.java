package com.ac.moduleapi.service.quizlog.query;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizlog.QuizLog;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quizlog.QuizLogRepository;
import com.ac.modulecommon.repository.quizlog.query.QuizLogQueryRepository;
import com.ac.modulecommon.repository.quizlog.query.QuizLogTraceQueryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.ac.modulecommon.entity.quiz.QuizLevel.BOJ_SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizLogQueryServiceTest {

    @InjectMocks
    private QuizLogQueryService quizLogQueryService;

    @Mock
    private QuizLogQueryRepository quizLogQueryRepository;

    @Mock
    private QuizLogRepository quizLogRepository;

    private User mockUser;
    private Quiz mockQuiz;

    @BeforeEach
    public void init() {
        mockUser = User.builder().id(1L)
                .oauthId(12345L)
                .nickname("mock")
                .profileImage("mock")
                .build();

        mockQuiz = Quiz.builder().id(1L)
                .title("mockTitle")
                .quizUrl("mockUrl")
                .level(BOJ_SILVER)
                .platform(BOJ).build();
    }

    @Test
    @DisplayName("문제_푼_날은_1을_반환하고_문제를_풀지_않은_경우_0을_반환한다")
    void getQuizLogTraces_1() {

        //given
        QuizLogTraceQueryDto solved = new QuizLogTraceQueryDto("2022-07-16", SolvedState.SOLVED);
        QuizLogTraceQueryDto unsolved = new QuizLogTraceQueryDto("2022-07-17", SolvedState.UNSOLVED);
        QuizLogTraceQueryDto notPicked = new QuizLogTraceQueryDto("2022-07-18", SolvedState.NOT_PICKED);

        List<QuizLogTraceQueryDto> mocks = Arrays.asList(solved, unsolved, notPicked);
        given(quizLogQueryRepository.getQuizLogTraces(anyLong(), any(), any())).willReturn(mocks);
        Long mockUserId = 1L;

        //when
        List<QuizLogTraceDto> quizLogTraces = quizLogQueryService.getQuizLogTraces(mockUserId, LocalDate.now());

        //then
        assertThat(quizLogTraces.size()).isEqualTo(mocks.size());
        LocalDate solvedDate = LocalDate.of(2022, 7, 16);
        LocalDate unSolvedDate = LocalDate.of(2022, 7, 17);
        LocalDate notPickedDate = LocalDate.of(2022, 7, 18);

        quizLogTraces.forEach(result -> {
            if (result.getDate().isEqual(solvedDate)) {
                assertThat(result.getState()).isEqualTo(1);
            } else if (result.getDate().isEqual(unSolvedDate)){
                assertThat(result.getState()).isEqualTo(0);
            } else if (result.getDate().isEqual(notPickedDate)) {
                assertThat(result.getState()).isEqualTo(0);
            }
        });

        verify(quizLogQueryRepository).getQuizLogTraces(anyLong(), any(), any());
    }

    @Test
    @DisplayName("userId나_LocalDate_값이_없으면_예외를_반환한다")
    public void getQuizLogTraces_2() {
        //given
        Long mockUserId = 1L;

        //when
        Assertions.assertThatThrownBy(() -> quizLogQueryService.getQuizLogTraces(null, LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> quizLogQueryService.getQuizLogTraces(mockUserId, null))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizLogQueryRepository, never()).getQuizLogTraces(anyLong(), any(), any());
    }

    @Test
    @DisplayName("사용자가 최초로 사용한 시점을 반환한다")
    public void getFirstUsageTime_1() {
        //given
        Long mockUserId = 1L;
        QuizLog quizLog = QuizLog.builder()
                .quiz(mockQuiz)
                .user(mockUser)
                .solvedState(SolvedState.UNSOLVED)
                .build();

        given(quizLogRepository.findFirstByUserId(anyLong())).willReturn(Optional.ofNullable(quizLog));

        //when
        LocalDateTime firstUsageTime = quizLogQueryService.getFirstUsageTime(mockUserId);

        //then
        assertThat(firstUsageTime).isEqualTo(quizLog.getCreatedAt());
        verify(quizLogRepository).findFirstByUserId(anyLong());
    }

    @Test
    @DisplayName("사용자가 최초로 사용한 시점이 없으면 예외를 반환한다")
    public void getFirstUsageTime_2() {
        //given
        Long mockUserId = 1L;

        given(quizLogRepository.findFirstByUserId(anyLong())).willReturn(Optional.empty());

        //when
        Assertions.assertThatThrownBy(() -> quizLogQueryService.getFirstUsageTime(mockUserId))
                .isInstanceOf(ApiException.class);

        //then
        verify(quizLogRepository).findFirstByUserId(anyLong());
    }

    @Test
    @DisplayName("userId가 없으면 예외를 반환한다")
    public void getFirstUsageTime_3() {
        //given
        Long invalidUserId = null;

        //when
        Assertions.assertThatThrownBy(() -> quizLogQueryService.getFirstUsageTime(invalidUserId))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizLogRepository, never()).findFirstByUserId(anyLong());
    }
}