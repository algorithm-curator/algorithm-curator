package com.ac.moduleapi.service.user;

import com.ac.modulecommon.entity.user.User;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.user.UserRepository;
import com.ac.modulecommon.util.UploadUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UploadUtils uploadUtils;

    private User mockUser;

    @BeforeEach
    public void init() {
        mockUser = User.builder().id(1L)
                .oauthId(12345L)
                .nickname("mock")
                .profileImage("mock")
                .build();
    }

    @Test
    public void oauthId가_주어지면_회원가입을_할_수_있다() throws ExecutionException, InterruptedException {
        //given
        Long mockUserId = 1L;
        Long mockOauthId = 12345L;
        User user = User.builder().id(mockUserId).oauthId(mockOauthId).build();

        given(userRepository.save(any())).willReturn(user);
        given(userRepository.existsByOAuth(anyLong())).willReturn(false);

        //when
        Long oauthId = 12345L;
        Long userId = userService.create(oauthId).get();

        //then
        verify(userRepository).save(any());
        verify(userRepository).existsByOAuth(anyLong());
        verify(userRepository, never()).findByOauthId(anyLong());

        assertThat(userId).isEqualTo(user.getId());
        assertThat(oauthId).isEqualTo(user.getOauthId());
    }

    @Test
    public void oauthId가_이미_존재하는_회원은_기존에_가입된_정보를_반환한다() throws ExecutionException, InterruptedException {
        //given
        Long mockUserId = 1L;
        Long mockOauthId = 12345L;
        User user = User.builder().id(mockUserId).oauthId(mockOauthId).build();

        given(userRepository.existsByOAuth(anyLong())).willReturn(true);
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.ofNullable(user));

        //when
        Long oauthId = 12345L;
        Long userId = userService.create(oauthId).get();

        //then
        verify(userRepository, never()).save(any());
        verify(userRepository).existsByOAuth(anyLong());
        verify(userRepository).findByOauthId(anyLong());

        assertThat(userId).isEqualTo(user.getId());
        assertThat(oauthId).isEqualTo(user.getOauthId());
    }

    @Test
    public void id를_가지고_회원을_조회할_수_있다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));

        //when
        User user = userService.getUser(mockUser.getId());

        //then
        assertThat(user.getId()).isEqualTo(mockUser.getId());
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void id에_해당하는_회원이_없는경우_예외를_반환한다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(ApiException.class, () -> userService.getUser(mockUser.getId()));

        //then
        verify(userRepository).findById(anyLong());
    }

    @Test
    public void oauth_id를_가지고_회원을_조회할_수_있다() {
        //given
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.ofNullable(mockUser));

        //when
        User user = userService.getUserByOauthId(mockUser.getOauthId());

        //then
        assertThat(user.getId()).isEqualTo(mockUser.getId());
        assertThat(user.getOauthId()).isEqualTo(mockUser.getOauthId());
        verify(userRepository).findByOauthId(anyLong());
    }

    @Test
    public void oauth_id에_해당하는_회원이_없는경우_예외를_반환한다() {
        //given
        given(userRepository.findByOauthId(anyLong())).willReturn(Optional.empty());

        //when
        assertThrows(ApiException.class, () -> userService.getUserByOauthId(mockUser.getOauthId()));

        //then
        verify(userRepository).findByOauthId(anyLong());
    }

    @Test
    public void 회원정보_중_닉네임만_수정할_수_있다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(false);
        Long userId = mockUser.getId();
        String newNickname = "new nickname";

        //when
        userService.update(userId, newNickname);

        //then
        assertThat(mockUser.getNickname()).isEqualTo(newNickname);
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
    }

    @Test
    public void 닉네임_등록_시_중복된_경우_예외를_반환한다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(true);
        Long userId = mockUser.getId();
        String duplicatedNickname = "duplicated nickname";

        //when
        Assertions.assertThrows(IllegalArgumentException.class,() -> userService.update(userId, duplicatedNickname));

        //then
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
    }

    @Test
    public void 닉네임만_수정_시_중복된_닉네임이어도_기존의_본인_닉네임이면_예외를_반환하지_않는다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(true);
        Long userId = mockUser.getId();
        String userNickname = mockUser.getNickname();
        String newNickname = userNickname;

        //when
        userService.update(userId, newNickname);

        //then
        assertThat(mockUser.getNickname()).isEqualTo(newNickname);
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
    }

    @Test
    public void 회원정보_중_닉네임과_프로필_이미지를_수정할_수_있다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(false);
        given(uploadUtils.isNotImageFile(anyString())).willReturn(false);
        Long userId = mockUser.getId();
        String newNickname = "new nickname";
        String newProfileImage = "new profileImage";

        //when
        userService.update(userId, newNickname, newProfileImage);

        //then
        assertThat(mockUser.getNickname()).isEqualTo(newNickname);
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
        verify(uploadUtils).isNotImageFile(anyString());
    }

    @Test
    public void 회원정보_중_닉네임과_프로필_이미지를_수정_시_닉네임이_중복되면_예외를_반환한다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(true);
        given(uploadUtils.isNotImageFile(anyString())).willReturn(false);
        Long userId = mockUser.getId();
        String duplicatedNickname = "duplicated nickname";
        String newProfileImage = "new profileImage";

        //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userId, duplicatedNickname, newProfileImage));

        //then
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
        verify(uploadUtils).isNotImageFile(anyString());
    }

    @Test
    public void 닉네임과_이미지_수정_시_중복된_닉네임이어도_기존의_본인_닉네임이면_예외를_반환하지_않는다() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(mockUser));
        given(userRepository.exists(anyString())).willReturn(true);
        given(uploadUtils.isNotImageFile(anyString())).willReturn(false);
        Long userId = mockUser.getId();
        String userNickname = mockUser.getNickname();
        String newNickname = userNickname;
        String newProfileImage = "new profileImage";

        //when
        userService.update(userId, newNickname, newProfileImage);

        //then
        assertThat(mockUser.getNickname()).isEqualTo(newNickname);
        verify(userRepository).findById(anyLong());
        verify(userRepository).exists(anyString());
        verify(uploadUtils).isNotImageFile(anyString());
    }

    @Test
    public void 프로필_이미지_업로드_시_이미지_파일_포맷이_아니면_예외를_반환한다() {
        //given
        given(uploadUtils.isNotImageFile(anyString())).willReturn(true);
        Long userId = mockUser.getId();
        String userNickname = mockUser.getNickname();
        String newNickname = userNickname;
        String newProfileImage = "new profileImage";

        //when
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.update(userId, newNickname, newProfileImage));

        //then
        assertThat(mockUser.getNickname()).isEqualTo(newNickname);
        verify(userRepository, never()).findById(anyLong());
        verify(userRepository, never()).exists(anyString());
        verify(uploadUtils).isNotImageFile(anyString());
    }

    @Test
    public void 닉네임_중복여부_체크_시_새로운_닉네임은_true를_반환한다() {
        //given
        given(userRepository.exists(anyString())).willReturn(false);
        String nickname = "new nickname";

        //when
        boolean isUnique = userService.isUniqueNickname(nickname);

        //then
        assertThat(isUnique).isEqualTo(true);
        verify(userRepository).exists(anyString());
    }

    @Test
    public void 닉네임_중복여부_체크_시_기존에_존재하는_닉네임은_false를_반환한다() {
        //given
        given(userRepository.exists(anyString())).willReturn(true);
        String nickname = "existed nickname";

        //when
        boolean isUnique = userService.isUniqueNickname(nickname);

        //then
        assertThat(isUnique).isEqualTo(false);
        verify(userRepository).exists(anyString());
    }
}