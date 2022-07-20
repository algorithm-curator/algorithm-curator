package com.ac.moduleapi.service.quizlog.query;

import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.repository.quizlog.query.QuizLogQueryRepository;
import com.ac.modulecommon.repository.quizlog.query.QuizLogTraceQueryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

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

    @Test
    void 문제_푼_날은_1을_반환하고_문제를_풀지_않은_경우_0을_반환한다() {

        //given
        QuizLogTraceQueryDto solved = new QuizLogTraceQueryDto("2022-07-16", SolvedState.SOLVED);
        QuizLogTraceQueryDto unsolved = new QuizLogTraceQueryDto("2022-07-17", SolvedState.UNSOLVED);
        QuizLogTraceQueryDto notPicked = new QuizLogTraceQueryDto("2022-07-18", SolvedState.NOT_PICKED);

        List<QuizLogTraceQueryDto> mocks = Arrays.asList(solved, unsolved, notPicked);
        given(quizLogQueryRepository.getQuizLogTraces(anyLong(), any())).willReturn(mocks);
        Long mockUserId = 1L;

        //when
        List<QuizLogTraceDto> quizLogTraces = quizLogQueryService.getQuizLogTraces(mockUserId, LocalDateTime.now());

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

        verify(quizLogQueryRepository).getQuizLogTraces(anyLong(), any());
    }

    @Test
    public void userId나_LocalDateTime_값이_없으면_예외를_반환한다() {
        //given
        Long mockUserId = 1L;

        Assertions.assertThatThrownBy(() -> quizLogQueryService.getQuizLogTraces(null, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> quizLogQueryService.getQuizLogTraces(mockUserId, null))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizLogQueryRepository, never()).getQuizLogTraces(anyLong(), any());
    }
}