package com.ac.modulecommon.entity.quiztype;

import com.ac.modulecommon.entity.AuditingCreateUpdateEntity;
import com.ac.modulecommon.entity.quiz.Quiz;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quiz_type_mappings")
public class QuizTypeMapping extends AuditingCreateUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private QuizType quizType;

    @Builder
    private QuizTypeMapping(Long id, Quiz quiz, QuizType quizType) {
        this.id = id;
        this.quiz = quiz;
        this.quizType = quizType;
    }
}
