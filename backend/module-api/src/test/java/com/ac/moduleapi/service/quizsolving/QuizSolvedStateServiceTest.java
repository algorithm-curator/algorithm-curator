package com.ac.moduleapi.service.quizsolving;

import com.ac.moduleapi.service.quiz.QuizService;
import com.ac.moduleapi.service.user.UserService;
import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quiz.query.QuizQueryDto;
import com.ac.modulecommon.repository.quiz.query.QuizQueryRepository;
import com.ac.modulecommon.repository.quizsolving.QuizSolvedStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.ac.modulecommon.entity.quiz.QuizLevel.BOJ_GOLD;
import static com.ac.modulecommon.entity.quiz.QuizLevel.BOJ_SILVER;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.BOJ;
import static com.ac.modulecommon.entity.quiz.QuizPlatform.PG;
import static com.ac.modulecommon.entity.quizsolving.SolvedState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class QuizSolvedStateServiceTest {

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
                                .oauthId(12345L)
                                .nickname("mock")
                                .profileImage("mock")
                                .build();

        mockQuiz = Quiz.builder().id(1L)
                                .title("mockTitle")
                                .quizUrl("mockUrl")
                                .level(BOJ_SILVER)
                                .platform(BOJ).build();

        mockSolvedState = QuizSolvedState.builder()
                .id(1L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(UNSOLVED)
                .build();
    }

    /**
     * start test createRandomQuizzes
     */
    @Test
    public void 뽑힌_데이터_중_count_만큼만_결과를_반환한다_insert_case() throws ExecutionException, InterruptedException {
        //given
        int loopSize = 100;
        List<QuizQueryDto> dtoList = new ArrayList<>();
        for (int loop = 1; loop <= loopSize; ++loop) {
            Long id = null;
            QuizQueryDto dto = new QuizQueryDto(id, 1L, "title2", "www.naver.com", BOJ_GOLD, PG);
            dtoList.add(dto);
        }

        given(quizQueryRepository.findAll(any(), any())).willReturn(dtoList);
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.save(any())).willReturn(mockSolvedState);

        int count = 3;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count).get();

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, times(count)).getQuiz(any());
        verify(quizSolvedStateRepository, times(count)).save(any());
        verify(quizSolvedStateRepository, never()).findById(anyLong());
    }

    @Test
    public void 뽑힌_데이터_중_count_만큼만_결과를_반환한다_update_case() throws ExecutionException, InterruptedException {
        //given
        int loopSize = 100;
        List<QuizQueryDto> dtoList = new ArrayList<>();
        for (int loop = 1; loop <= loopSize; ++loop) {
            Long id = (long)loop;
            QuizQueryDto dto = new QuizQueryDto(id, 1L, "title2", "www.naver.com", BOJ_GOLD, PG);
            dtoList.add(dto);
        }

        given(quizQueryRepository.findAll(any(), any())).willReturn(dtoList);
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 3;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count).get();

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, never()).getQuiz(any());
        verify(quizSolvedStateRepository, never()).save(any());
        verify(quizSolvedStateRepository, times(count)).findOne(anyLong());
    }

    @Test
    public void 이미_저장된_정보는_update문으로_상태가_변경되고_새로_저장된_정보만_insert_된다() {
        //given
        QuizQueryDto nullDto1 = new QuizQueryDto(null, 1L, "title1", "www.naver.com", BOJ_SILVER, BOJ);
        QuizQueryDto notNullDto1 = new QuizQueryDto(1L, 1L, "title2", "www.naver.com", BOJ_GOLD, PG);
        QuizQueryDto notNullDto2 = new QuizQueryDto(2L, 1L, "title3", "www.naver.com", BOJ_GOLD, BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(nullDto1,
                                                                            notNullDto1,
                                                                            notNullDto2));

        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.save(any())).willReturn(mockSolvedState);
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 3;

        //when
        quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count);

        //then
        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, times(1)).getQuiz(any());
        verify(quizSolvedStateRepository, times(1)).save(any());
        verify(quizSolvedStateRepository, times(2)).findOne(anyLong());
    }

    @Test
    public void quizSolvedStateId가_null_인_데이터는_새_id를_할당받고_UNSOLVED_의_QuizSolvedState가_생성된다() throws ExecutionException, InterruptedException {
        //given
        QuizQueryDto dto = new QuizQueryDto(null,
                mockQuiz.getId(),
                "title1",
                "www.naver.com",
                BOJ_SILVER,
                BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(dto));
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizService.getQuiz(anyLong())).willReturn(mockQuiz);
        given(quizSolvedStateRepository.save(any())).willReturn(mockSolvedState);
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        int count = 1;

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count).get();
        Long id = randomQuizzes.get(0).getQuizSolvedStateId();

        //then
        assertThat(randomQuizzes.size()).isEqualTo(count);
        assertThat(id).isNotNull();

        // 상태 조회를 위해 findById로 조회
        QuizSolvedState solvedState = quizSolvedStateService.getQuizSolvedState(id);
        assertThat(solvedState.getSolvedState()).isEqualTo(SolvedState.UNSOLVED);

        verify(quizQueryRepository).findAll(any(), any());
        verify(userService).getUser(anyLong());
        verify(quizService, Mockito.times(count)).getQuiz(any());
        verify(quizSolvedStateRepository, Mockito.times(count)).save(any());
        verify(quizSolvedStateRepository).save(any());
        verify(quizSolvedStateRepository).findOne(anyLong());
    }

    @Test
    public void quizSolvedStateId가_이미_있던_NOT_PICKED_상태의_데이터는_UNSOLVED_상태로_변경된다() throws ExecutionException, InterruptedException {
        //given
        QuizSolvedState notPickedSolvedState = QuizSolvedState.builder()
                .id(5L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(NOT_PICKED)
                .build();

        QuizQueryDto dto = new QuizQueryDto(mockSolvedState.getId(),
                mockQuiz.getId(),
                "title1",
                "www.naver.com",
                BOJ_SILVER,
                BOJ);

        given(quizQueryRepository.findAll(any(), any())).willReturn(List.of(dto));
        given(userService.getUser(anyLong())).willReturn(mockUser);
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(notPickedSolvedState));

        int count = 1;

        QuizSolvedState beforeSolvedState = quizSolvedStateService.getQuizSolvedState(notPickedSolvedState.getId());
        assertThat(beforeSolvedState.getSolvedState()).isEqualTo(SolvedState.NOT_PICKED);

        //when
        List<QuizQueryDto> randomQuizzes = quizSolvedStateService.createRandomQuizzes(mockUser.getId(), count).get();
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
        verify(quizSolvedStateRepository, Mockito.times(3)).findOne(anyLong());
    }

    @Test
    public void 한_개에서_세_개까지의_갯수만_요청할_수_있다() {
        //given
        int overCountRequest = 4;
        int underCountRequest = 0;

        //when
        assertThatThrownBy(() -> quizSolvedStateService.createRandomQuizzes(mockUser.getId(), overCountRequest))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> quizSolvedStateService.createRandomQuizzes(mockUser.getId(), underCountRequest))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizQueryRepository, never()).findAll(any(), any());
        verify(userService, never()).getUser(anyLong());
        verify(quizService, never()).getQuiz(any());
        verify(quizSolvedStateRepository, never()).save(any());
        verify(quizSolvedStateRepository, never()).findOne(anyLong());
    }
    /**
     * end test createRandomQuizzes
     */

    /**
     * start test create
     */
    @Test
    public void 유저_엔티티나_quizId가_null이면_quizSolvedState_생성_시_예외를_반환한다() {
        //given
        //when
        assertThatThrownBy(() -> quizSolvedStateService.create(null, mockQuiz.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> quizSolvedStateService.create(mockUser, null))
                .isInstanceOf(IllegalArgumentException.class);
        //then
        verify(quizService, never()).getQuiz(anyLong());
        verify(quizSolvedStateRepository, never()).save(any());
    }
    /**
     * end test create
     */

    /**
     * start test getQuizSolvedStates
     */
    @Test
    public void QuizSolvedStates를_조회할_수_있다() {
        //given
        List<QuizSolvedState> list = Arrays.asList(mockSolvedState, mockSolvedState, mockSolvedState, mockSolvedState);
        given(quizSolvedStateRepository.findAll(anyLong(), any(), any())).willReturn(list);
        Pageable pageable = PageRequest.of(0, list.size());

        //when
        List<QuizSolvedState> quizSolvedStates = quizSolvedStateService.getQuizSolvedStates(mockUser.getId(), pageable);

        //then
        assertThat(quizSolvedStates.size()).isEqualTo(list.size());
        verify(quizSolvedStateRepository).findAll(anyLong(), any(), any());
    }

    @Test
    public void 특정_상태에_있는_QuizSolvedStates를_조회할_수_있다() {
        //given
        QuizSolvedState solvedState1 = QuizSolvedState.builder()
                .id(2L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(SOLVED)
                .build();

        QuizSolvedState solvedState2 = QuizSolvedState.builder()
                .id(2L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(SOLVED)
                .build();


        List<QuizSolvedState> list = Arrays.asList(solvedState1, solvedState2);
        given(quizSolvedStateRepository.findAll(anyLong(), any(), any())).willReturn(list);
        Pageable pageable = PageRequest.of(0, list.size());

        //when
        List<QuizSolvedState> quizSolvedStates = quizSolvedStateService.getQuizSolvedStates(mockUser.getId(), UNSOLVED, pageable);

        //then
        assertThat(quizSolvedStates.size()).isEqualTo(list.size());
        assertThat(quizSolvedStates.get(0).getSolvedState()).isEqualTo(SOLVED);
        verify(quizSolvedStateRepository).findAll(anyLong(), any(), any());
    }

    @Test
    public void NOT_PICKED_상태는_조회_시_예외를_반환한다() {
        //given
        QuizSolvedState notPickedState = QuizSolvedState.builder()
                .id(2L)
                .user(mockUser)
                .quiz(mockQuiz)
                .solvedState(NOT_PICKED)
                .build();

        List<QuizSolvedState> list = Arrays.asList(mockSolvedState, mockSolvedState, mockSolvedState);
        Pageable pageable = PageRequest.of(0, list.size());

        //when
        assertThatThrownBy(() -> quizSolvedStateService.getQuizSolvedStates(mockUser.getId(), NOT_PICKED, pageable))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizSolvedStateRepository, never()).findAll(anyLong(), any(), any());
    }
    /**
     * end test getQuizSolvedStates
     */

    /**
     * start test getQuizSolvedState
     */
    @Test
    public void 단_건_조회_시_데이터를_찾지_못하면_예외를_반환한다() {
        //given
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.empty());

        //when
        assertThatThrownBy(() -> quizSolvedStateService.getQuizSolvedState(mockUser.getId()))
                .isInstanceOf(ApiException.class);

        //then
        verify(quizSolvedStateRepository).findOne(anyLong());
    }
    /**
     * end test getQuizSolvedState
     */

    /**
     * start test getUnsolvedQuizSize, getSolvedQuizSize
     */
    @Test
    public void 내가_해결한_문제와_해결하지_못한_문제_갯수를_조회할_수_있다() {
        //given
        int countSize = 5;
        given(quizSolvedStateRepository.countBySolvedState(anyLong(), any())).willReturn(countSize);

        //when
        int unsolvedQuizSize = quizSolvedStateService.getUnsolvedQuizSize(mockUser.getId());
        int solvedQuizSize = quizSolvedStateService.getSolvedQuizSize(mockUser.getId());

        //then
        assertThat(unsolvedQuizSize).isEqualTo(countSize);
        assertThat(solvedQuizSize).isEqualTo(countSize);
        verify(quizSolvedStateRepository, times(2)).countBySolvedState(anyLong(), any());
    }

    @Test
    public void 문제_상태를_변경할_수_있다() {
        //given
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);
        SolvedState newState = SolvedState.SOLVED;

        //when
        quizSolvedStateService.update(mockSolvedState.getId(), newState, mockUser.getId());

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(newState);
        verify(quizSolvedStateRepository).findOne(anyLong());
    }

    /**
     * start test update
     */
    @Test
    public void 안_뽑음_상태로는_변경할_수_없다() {
        //given
        //when
        assertThatThrownBy(() -> quizSolvedStateService.update(mockSolvedState.getId(), NOT_PICKED, mockUser.getId()))
                .isInstanceOf(IllegalArgumentException.class);

        //then
        verify(quizSolvedStateRepository, never()).findOne(anyLong());
    }

    @Test
    public void 내가_뽑은_문제가_아니면_문제_상태를_변경할_수_없다() {
        //given
        User otherUser = User.builder().id(2L)
                .oauthId(6789L)
                .nickname("mock")
                .profileImage("mock")
                .build();

        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);
        SolvedState newState = SolvedState.SOLVED;

        //when
        Assertions.assertThrows(ApiException.class,
            () -> quizSolvedStateService.update(mockSolvedState.getId(), newState, otherUser.getId()));

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);
        verify(quizSolvedStateRepository).findOne(anyLong());
    }

    @Test
    public void 주어진_문제를_안뽑음_상태로_변경할_수_있다() {
        //given
        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);

        //when
        quizSolvedStateService.delete(mockSolvedState.getId(), mockUser.getId());

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(SolvedState.NOT_PICKED);
        verify(quizSolvedStateRepository).findOne(anyLong());
    }

    @Test
    public void 내가_뽑은_문제가_아니면_안뽑음_상태로_변경할_수_없다() {
        //given
        User otherUser = User.builder().id(2L)
                .oauthId(6789L)
                .nickname("mock")
                .profileImage("mock")
                .build();

        given(quizSolvedStateRepository.findOne(anyLong())).willReturn(Optional.ofNullable(mockSolvedState));

        //초기 상태: NOT_PICKED
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);

        //when
        Assertions.assertThrows(ApiException.class,
                () -> quizSolvedStateService.delete(mockSolvedState.getId(), otherUser.getId()));

        //then
        assertThat(mockSolvedState.getSolvedState()).isEqualTo(UNSOLVED);
        verify(quizSolvedStateRepository).findOne(anyLong());
    }
}