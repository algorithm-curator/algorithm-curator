package com.ac.modulecommon.repository.quizsolving;

import com.ac.modulecommon.entity.quizsolving.QuizSolvedState;
import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.ac.modulecommon.entity.quiz.QQuiz.quiz;
import static com.ac.modulecommon.entity.quizsolving.QQuizSolvedState.quizSolvedState;
import static com.ac.modulecommon.entity.user.QUser.user;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class QuizSolvedStateCustomRepositoryImpl implements QuizSolvedStateCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QuizSolvedState> findOne(Long id) {
        QuizSolvedState result = jpaQueryFactory
                .selectFrom(quizSolvedState)
                    .join(quizSolvedState.user, user).fetchJoin()
                    .join(quizSolvedState.quiz, quiz).fetchJoin()
                .where(quizSolvedState.id.eq(id))
                .fetchOne();

        return ofNullable(result);
    }

    @Override
    public List<QuizSolvedState> findAll(Long userId, SolvedState solvedState, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(quizSolvedState)
                    .join(quizSolvedState.user, user).fetchJoin()
                    .join(quizSolvedState.quiz, quiz).fetchJoin()
                .where(
                    quizSolvedState.user.id.eq(userId),
                    solvedStateEq(solvedState)
                )
                .orderBy(quizSolvedState.updatedAt.desc())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                .fetch();
    }

    @Override
    public int countAllBySolvedState(Long userId, SolvedState solvedState) {
        return jpaQueryFactory
                .selectFrom(quizSolvedState)
                    .join(quizSolvedState.user, user).fetchJoin()
                .where(
                    quizSolvedState.user.id.eq(userId),
                    quizSolvedState.solvedState.eq(solvedState)
                )
                .fetch()
                .size();
    }

    private BooleanExpression solvedStateEq(SolvedState solvedState) {
        if (solvedState == null) {
            return quizSolvedState.solvedState.ne(SolvedState.NOT_PICKED);
        }

        return quizSolvedState.solvedState.eq(solvedState);
    }
}
