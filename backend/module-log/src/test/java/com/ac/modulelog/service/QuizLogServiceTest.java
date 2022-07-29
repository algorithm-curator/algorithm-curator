package com.ac.modulelog.service;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizlog.QuizLog;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.repository.quizlog.QuizLogRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ac.modulecommon.entity.quiz.QuizLevel.BOJ_SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static com.ac.modulecommon.entity.quizsolving.SolvedState.UNSOLVED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuizLogServiceTest {

    @InjectMocks
    private QuizLogServiceImpl quizLogService;

    @Mock
    private QuizLogRepository quizLogRepository;

    private Quiz mockQuiz;
    private User mockUser;
    private QuizLog mockQuizLog;

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

        mockQuizLog = QuizLog.builder()
                .id(1L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(UNSOLVED)
                .build();
    }

    @Test
    @DisplayName("user, quiz, solvedState가 주어지면 quizLog를 생성할 수 있다")
    public void createQuizLog_1() {

        //given
        given(quizLogRepository.save(any())).willReturn(mockQuizLog);

        //when
        Long quizLog = quizLogService.createQuizLog(mockUser, mockQuiz, UNSOLVED);

        //then
        assertThat(quizLog).isEqualTo(mockQuizLog.getId());
        verify(quizLogRepository).save(any());
    }

    @Test
    @DisplayName("user, quiz, solvedState중에 하나라도 null이면 예외를 반환한다")
    public void createQuizLog_2() {
        //given
        User invalidUser = null;
        Quiz invalidQuiz = null;
        SolvedState invalidSolvedState = null;

        //when
        Assertions.assertThatThrownBy(() -> quizLogService.createQuizLog(invalidUser, mockQuiz, UNSOLVED))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> quizLogService.createQuizLog(mockUser, invalidQuiz, UNSOLVED))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> quizLogService.createQuizLog(mockUser, mockQuiz, invalidSolvedState))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizLogRepository, never()).save(any());
    }
}