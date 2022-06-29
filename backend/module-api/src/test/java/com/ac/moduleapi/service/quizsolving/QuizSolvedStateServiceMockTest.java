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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ac.modulecommon.entity.quiz.QuizLevel.GOLD;
import static com.ac.modulecommon.entity.quiz.QuizLevel.SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.PRGRMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * 서비스 로직이 의도대로 동작하는지 확인하기 위해 @Mock 으로만 테스트 구성
 * SpringContainer 의 도움을 받아야 하는 부분은 SpyTest 로 분리
 */
@ExtendWith(MockitoExtension.class)
class QuizSolvedStateServiceMockTest {

    @InjectMocks
    private QuizSolvedStateServiceImpl quizSolvedStateService;

    @Mock
    private QuizSolvedStateRepository quizSolvedStateRepository;

    @Mock
    private QuizQueryRepository quizQueryRepository;

    @Mock
    private QuizService quizService;

    @Mock
    private UserService userService;

    private Quiz mockQuiz;
    private User mockUser;
    private QuizSolvedState mockSolvedState;

    @BeforeEach
    public void init() {
        mockUser = User.builder().id(1L)
                                .nickname("mock")
                                .profileImage("mock")
                                .build();

        mockQuiz = Quiz.builder().id(1L)
                                .title("mockTitle")
                                .quizUrl("mockUrl")
                                .level(SILVER)
                                .platform(BOJ).build();

        mockSolvedState = QuizSolvedState.builder()
                .id(1L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(SolvedState.UNSOLVED)
                .build();
    }

    @Test
    public void 뽑힌_데이터_중_count_만큼만_결과를_반환한다_insert_case() {
        //given
        int loopSize = 100;
        List<QuizQueryDto> dtoList = new ArrayList<>();
        for (int loop = 1; loop <= loopSize; ++loop) {
            Long id = null;
            QuizQueryDto dto = new QuizQueryDto(id, 1L, "title2", "www.naver.com", GOLD, PRGRMS);
            dtoList.add(dto);
        }

        given(quizQueryRepository.findAll(any(), any())).willReturn(dtoList);
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.save(any())).willReturn(mockSolvedState);

        int count = 5;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, times(count)).getQuiz(any());
        verify(quizSolvedStateRepository, times(count)).save(any());
        verify(quizSolvedStateRepository, never()).findById(anyLong());
    }

    @Test
    public void 뽑힌_데이터_중_count_만큼만_결과를_반환한다_update_case() {
        //given
        int loopSize = 100;
        List<QuizQueryDto> dtoList = new ArrayList<>();
        for (int loop = 1; loop <= loopSize; ++loop) {
            Long id = (long)loop;
            QuizQueryDto dto = new QuizQueryDto(id, 1L, "title2", "www.naver.com", GOLD, PRGRMS);
            dtoList.add(dto);
        }

        given(quizQueryRepository.findAll(any(), any())).willReturn(dtoList);
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizSolvedStateRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 5;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, never()).getQuiz(any());
        verify(quizSolvedStateRepository, never()).save(any());
        verify(quizSolvedStateRepository, times(count)).findById(anyLong());
    }

    @Test
    public void 이미_저장된_정보는_update문으로_상태가_변경되고_새로_저장된_정보만_insert_된다() {
        //given
        QuizQueryDto nullDto1 = new QuizQueryDto(null, 1L, "title1", "www.naver.com", SILVER, BOJ);
        QuizQueryDto notNullDto1 = new QuizQueryDto(1L, 1L, "title2", "www.naver.com", GOLD, PRGRMS);
        QuizQueryDto notNullDto2 = new QuizQueryDto(2L, 1L, "title3", "www.naver.com", GOLD, BOJ);
        QuizQueryDto notNullDto3 = new QuizQueryDto(3L, 1L, "title4", "www.naver.com", SILVER, PRGRMS);
        QuizQueryDto nullDto2 = new QuizQueryDto(null, 1L, "title5", "www.naver.com", GOLD, BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(nullDto1,
                                                                            notNullDto1,
                                                                            notNullDto2,
                                                                            notNullDto3,
                                                                            nullDto2));

        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.save(any())).willReturn(mockSolvedState);
        given(quizSolvedStateRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 5;

        //when
        quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);

        //then
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, times(2)).getQuiz(any());
        verify(quizSolvedStateRepository, times(2)).save(any());
        verify(quizSolvedStateRepository, times(3)).findById(anyLong());
    }

    @Test
    public void 문제_상태를_변경할_수_있다() {
        //given
        given(quizSolvedStateRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(SolvedState.UNSOLVED);
        SolvedState newState = SolvedState.SOLVED;

        //when
        quizSolvedStateService.update(mockSolvedState.getId(), newState);

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(newState);
        verify(quizSolvedStateRepository).findById(anyLong());
    }

    @Test
    public void 주어진_문제들을_안뽑음_상태로_변경_한다() {
        //given
        given(quizSolvedStateRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        List<Long> mockIds = List.of(mockSolvedState.getId(), mockSolvedState.getId(), mockSolvedState.getId());

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(SolvedState.UNSOLVED);

        //when
        quizSolvedStateService.delete(mockIds);

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(SolvedState.NOT_PICKED);
        verify(quizSolvedStateRepository, times(mockIds.size())).findById(anyLong());
    }
}