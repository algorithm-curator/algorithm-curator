package com.ac.modulecommon.entity.quizsolving;

import com.ac.modulecommon.entity.AuditingCreateUpdateEntity;
import com.ac.modulecommon.entity.quiz.Quiz;
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
@Table(name = "quiz_solved_states")
public class QuizSolvedState extends AuditingCreateUpdateEntity {

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

    public void update(SolvedState solvedState) {
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");

        this.solvedState = solvedState;
    }

    @Builder
    private QuizSolvedState(Long id, User user, Quiz quiz, SolvedState solvedState) {
        checkArgument(user != null, "user 값은 필수입니다.");
        checkArgument(quiz != null, "quiz 값은 필수입니다.");
        checkArgument(solvedState != null, "solvedState 값은 필수입니다.");

        this.id = id;
        this.user = user;
        this.quiz = quiz;
        this.solvedState = solvedState;
    }
}
