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

import static com.google.common.base.Preconditions.checkArgument;

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
        checkArgument(user != null, "user 값은 필수입니다.");
        checkArgument(quiz != null, "quiz 값은 필수입니다.");
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");

        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.solvedState = solvedState;
    }
}
