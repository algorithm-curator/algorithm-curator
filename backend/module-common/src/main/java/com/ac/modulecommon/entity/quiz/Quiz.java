package com.ac.modulecommon.entity.quiz;

import com.ac.modulecommon.entity.AuditingCreateUpdateEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
        this.id = id;
        this.title = title;
        this.quizUrl = quizUrl;
        this.level = level;
        this.platform = platform;
    }
}
