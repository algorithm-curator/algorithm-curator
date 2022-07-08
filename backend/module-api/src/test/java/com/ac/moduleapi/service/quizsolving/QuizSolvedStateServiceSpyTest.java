package com.ac.moduleapi.service.quizsolving;

import com.ac.moduleapi.service.quiz.QuizService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.repository.quiz.query.QuizQueryDto;
import com.ac.modulecommon.repository.quiz.query.QuizQueryRepository;
import com.ac.modulecommon.repository.quizsolving.QuizSolvedStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static com.ac.modulecommon.entity.quiz.QuizLevel.SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * 핵심 로직인 createRandomQuizzes 에서 실제 데이터가 들어가고, 상태가 변경되는지 확인하기 위해
 * Spring Container 와 함께 @SpyBean 으로 별도의 Test 구성
 */
@SpringBootTest
class QuizSolvedStateServiceSpyTest {

    @Autowired
    private QuizSolvedStateService quizSolvedStateService;

    @SpyBean
    private QuizSolvedStateRepository quizSolvedStateRepository;

    @MockBean
    private QuizQueryRepository quizQueryRepository;

    @MockBean
    private QuizService quizService;

    @MockBean
    private UserService userService;

    private Quiz mockQuiz;
    private User mockUser;
    private QuizSolvedState mockSolvedState;

    @BeforeEach
    public void init() {
        mockUser = User.builder().id(1L)
                                .oauthId(12345L)
                                .nickname("mock")
                                .profileImage("mock").
                                build();

        mockQuiz = Quiz.builder().id(1L)
                                .title("mockTitle")
                                .quizUrl("mockUrl")
                                .level(SILVER)
                                .platform(BOJ).build();

        mockSolvedState = QuizSolvedState.builder()
                .id(1L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(SolvedState.NOT_PICKED)
                .build();
    }

    @Test
    public void quizSolvedStateId가_null_인_데이터는_새_id를_할당받고_UNSOLVED_의_QuizSolvedState가_생성된다() {
        //given
        QuizQueryDto dto = new QuizQueryDto(null,
                                            mockQuiz.getId(),
                                            "title1",
                                            "www.naver.com",
                                            SILVER,
                                            BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(dto));
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);

        int count = 1;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);
        Long id = randomQuizzes.get(0).getQuizSolvedStateId();

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        assertThat(id).isNotNull();

        // 상태 조회를 위해 findById로 조회
        QuizSolvedState solvedState = quizSolvedStateService.getQuizSolvedState(id);
        assertThat(solvedState.getSolvedState()).isEqualTo(SolvedState.UNSOLVED);

        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, times(count)).getQuiz(any());
        verify(quizSolvedStateRepository, times(count)).save(any());
    }

    @Test
    public void quizSolvedStateId가_이미_있는_데이터도_새_id를_할당받고_UNSOLVED_의_QuizSolvedState가_생성된다() {
        //given
        QuizQueryDto dto = new QuizQueryDto(mockSolvedState.getId(),
                                            mockQuiz.getId(),
                                            "title1",
                                            "www.naver.com",
                                            SILVER,
                                            BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(dto));
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 1;

        QuizSolvedState beforeSolvedState = quizSolvedStateService.getQuizSolvedState(mockSolvedState.getId());
        assertThat(beforeSolvedState.getSolvedState()).isEqualTo(SolvedState.NOT_PICKED);

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);
        Long id = randomQuizzes.get(0).getQuizSolvedStateId();

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);

        // 상태 조회를 위해 findById로 조회
        QuizSolvedState afterSolvedState = quizSolvedStateService.getQuizSolvedState(id);
        assertThat(afterSolvedState.getSolvedState()).isEqualTo(SolvedState.UNSOLVED);

        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, never()).getQuiz(any());
        verify(quizSolvedStateRepository, never()).save(any());
    }
}