package com.ac.moduleapi.service.quizsolving.query;

import com.ac.modulecommon.entity.quiztype.QuizType;
import com.ac.modulecommon.repository.quizsolving.query.QuizSolvedStateQueryRepository;
import com.ac.modulecommon.repository.quizsolving.query.SolvedCntByQuizTypeQueryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizSolvedStateQueryServiceTest {

    @InjectMocks
    private QuizSolvedStateQueryService quizSolvedStateQueryService;

    @Mock
    private QuizSolvedStateQueryRepository queryRepository;

    @Test
    void 사용자가_유형별로_문제_푼_갯수를_반환한다() {
        //given
        SolvedCntByQuizTypeQueryDto dpMock = new SolvedCntByQuizTypeQueryDto(QuizType.DP, 3L);
        SolvedCntByQuizTypeQueryDto dfsMock = new SolvedCntByQuizTypeQueryDto(QuizType.DFS, 5L);
        Long mockuserId = 1L;

        List<SolvedCntByQuizTypeQueryDto> mocks = Arrays.asList(dpMock, dfsMock);
        //given
        given(queryRepository.findAll(anyLong())).willReturn(mocks);

        //when
        List<SolvedCntByQuizTypeQueryDto> result = quizSolvedStateQueryService.getSolvedCountByQuizTypes(mockuserId);

        //then
        SolvedCntByQuizTypeQueryDto dpCandidate = result.stream()
                .filter(dto -> dto.getQuizType().equals(QuizType.DP))
                .findFirst().get();

        SolvedCntByQuizTypeQueryDto dfsCandidate = result.stream()
                .filter(dto -> dto.getQuizType().equals(QuizType.DFS))
                .findFirst().get();

        SolvedCntByQuizTypeQueryDto otherCandidate = result.stream()
                .filter(dto -> !dto.getQuizType().equals(QuizType.DFS) && !dto.getQuizType().equals(QuizType.DP))
                .findFirst().get();

        assertThat(result.size()).isEqualTo(QuizType.values().length);
        assertThat(dpCandidate.getCount()).isEqualTo(dpMock.getCount());
        assertThat(dfsCandidate.getCount()).isEqualTo(dfsMock.getCount());
        assertThat(otherCandidate.getCount()).isEqualTo(0L);
        verify(queryRepository).findAll(anyLong());
    }

    @Test
    public void userId값이_없으면_예외를_반환한다() {
        //given
        Assertions.assertThatThrownBy(() -> quizSolvedStateQueryService.getSolvedCountByQuizTypes(null))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(queryRepository, never()).findAll(anyLong());
    }
}