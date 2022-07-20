package com.ac.moduleapi.service.quiz;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quiz.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.ac.modulecommon.entity.quiz.QuizLevel.BOJ_SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @InjectMocks
    private QuizServiceImpl quizService;

    @Mock
    private QuizRepository quizRepository;

    private Quiz mockQuiz;

    @BeforeEach
    public void init() {
        mockQuiz = Quiz.builder().id(1L)
                .title("mockTitle")
                .quizUrl("mockUrl")
                .level(BOJ_SILVER)
                .platform(BOJ).build();
    }

    @Test
    void id에_null이_있으면_예외를_반환한다() {
        //given
        Long id = null;

        //when
        assertThatThrownBy(() -> quizService.getQuiz(id)).isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizRepository, never()).findById(anyLong());
    }

    @Test
    void id에_해당하는_퀴즈를_조회할_수_있다() {
        //given
        given(quizRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockQuiz));

        //when
        Quiz quiz = quizService.getQuiz(mockQuiz.getId());

        //then
        assertThat(quiz.getId()).isEqualTo(mockQuiz.getId());
        verify(quizRepository).findById(anyLong());
    }

    @Test
    void id에_해당하는_퀴즈가_없으면_예외를_반환한다() {
        //given
        given(quizRepository.findById(anyLong())).willReturn(Optional.empty());
        Long invalidId = 99L;

        //when
        assertThatThrownBy(() -> quizService.getQuiz(invalidId)).isInstanceOf(ApiException.class);

        //then
        verify(quizRepository).findById(anyLong());
    }

    @Test
    void 전체_퀴즈_갯수를_조회할_수_있다() {
        //given
        long mockQuizSize = 5;
        given(quizRepository.count()).willReturn(mockQuizSize);

        //when
        Long result = quizService.count();

        //then
        assertThat(result).isEqualTo(mockQuizSize);
        verify(quizRepository).count();
    }
}