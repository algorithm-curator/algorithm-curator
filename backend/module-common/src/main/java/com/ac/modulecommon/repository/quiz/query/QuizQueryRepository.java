package com.ac.modulecommon.repository.quiz.query;

import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ac.modulecommon.entity.quiz.QQuiz.quiz;
import static com.ac.modulecommon.entity.quizsolving.QQuizSolvedState.quizSolvedState;
import static com.ac.modulecommon.entity.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class QuizQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<QuizQueryDto> findAll(Long userId, Pageable pageable) {
        return jpaQueryFactory.select(
                    Projections.constructor(QuizQueryDto.class,
                        quizSolvedState.id,
                        quiz.id,
                        quiz.title,
                        quiz.quizUrl,
                        quiz.level,
                        quiz.platform
                    )
                )
                .from(quizSolvedState)
                    .join(quizSolvedState.user, user)
                        .on(quizSolvedState.user.id.eq(userId))
                    .rightJoin(quizSolvedState.quiz, quiz)
                .where(
                    quizSolvedState.solvedState.eq(SolvedState.NOT_PICKED)
                    .or(quizSolvedState.id.isNull())
                )
                .orderBy(quizSolvedState.updatedAt.asc().nullsFirst())
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                .fetch();
    }
}
