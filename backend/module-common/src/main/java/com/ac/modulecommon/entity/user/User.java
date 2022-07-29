package com.ac.modulecommon.entity.user;

import com.ac.modulecommon.entity.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long oauthId;

    @Column(length = 20)
    private String nickname;

    @Column(length = 250)
    private String profileImage;

    public void update(String nickname) {
        checkArgument(isNotBlank(nickname), "nickname 값은 필수입니다.");
        checkArgument(nickname.length() <= 20, "닉네임은 20자 이하여야 합니다.");

        this.nickname = nickname;
    }

    public void update(String nickname, String profileImage) {
        checkArgument(isNotBlank(nickname), "nickname 값은 필수입니다.");
        checkArgument(nickname.length() <= 20, "닉네임은 20자 이하여야 합니다.");
        checkArgument(isNotBlank(profileImage), "profileImage 값은 필수입니다.");
        checkArgument(profileImage.length() <= 250, "프로필 URL은 250자 이하여야 합니다.");

        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    @Builder
    private User(Long id, Long oauthId, String nickname, String profileImage) {
        checkArgument(oauthId != null, "oauthId 값은 필수입니다.");

        this.id = id;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
