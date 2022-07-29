package com.ac.modulecommon.entity.quiz;

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
@Table(name = "quizzes")
public class Quiz extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String quizUrl;

    private QuizLevel level;

    private QuizPlatform platform;

    @Builder
    private Quiz(Long id, String title, String quizUrl, QuizLevel level, QuizPlatform platform) {
        checkArgument(isNotBlank(title), "title 값은 필수입니다.");
        checkArgument(title.length() <= 50, "title 값은 50자 이하여야 합니다.");
        checkArgument(isNotBlank(quizUrl), "quizUrl 값은 필수입니다.");
        checkArgument(quizUrl.length() <= 250, "quizUrl 값은 250자 이하여야 합니다.");
        checkArgument(level != null, "level 값은 필수입니다.");
        checkArgument(platform != null, "platform 값은 필수입니다.");

        this.id = id;
        this.title = title;
        this.quizUrl = quizUrl;
        this.level = level;
        this.platform = platform;
    }
}
