package com.ac.modulecommon.entity.quizlog;

import com.ac.modulecommon.entity.AuditingCreateEntity;
import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.ac.modulecommon.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "quiz_logs")
public class QuizLog extends AuditingCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private SolvedState solvedState;

    @Builder
    private QuizLog(Long id, User user, Quiz quiz, SolvedState solvedState) {
        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.solvedState = solvedState;
    }
}
