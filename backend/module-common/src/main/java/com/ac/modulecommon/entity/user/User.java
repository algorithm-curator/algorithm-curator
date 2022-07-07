package com.ac.modulecommon.entity.user;

import com.ac.modulecommon.entity.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long oauthId;

    private String nickname;

    private String profileImage;

    @Builder
    private User(Long id, Long oauthId, String nickname, String profileImage) {
        this.id = id;
        this.oauthId = oauthId;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}
