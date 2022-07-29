package com.ac.modulecommon.repository.quizsolving.query;

import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ac.modulecommon.entity.quiz.QQuiz.quiz;
import static com.ac.modulecommon.entity.quizsolving.QQuizSolvedState.quizSolvedState;
import static com.ac.modulecommon.entity.quiztype.QQuizTypeMapping.quizTypeMapping;
import static com.ac.modulecommon.entity.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class QuizSolvedStateQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SolvedCntByQuizTypeQueryDto> findAll(Long userId) {
        return jpaQueryFactory
                    .select(
                        Projections.constructor(SolvedCntByQuizTypeQueryDto.class,
                            quizTypeMapping.quizType,
                            quizSolvedState.id.count()
                        )
                ).from(quizSolvedState)
                    .join(quizSolvedState.quiz, quiz)
                    .join(quizTypeMapping)
                        .on(quizSolvedState.quiz.id.eq(quizTypeMapping.quiz.id))
                .where(
                    quizSolvedState.user.id.eq(userId),
                    quizSolvedState.solvedState.eq(SolvedState.SOLVED)
                )
                .groupBy(quizTypeMapping.quizType)
                .fetch();
    }

    public List<QuizSolvedStateRankQueryDto> findAllRank(Pageable pageable) {
        return jpaQueryFactory
                    .select(
                        Projections.constructor(QuizSolvedStateRankQueryDto.class,
                            quizSolvedState.user.id,
                            quizSolvedState.user.nickname,
                            quizSolvedState.id.count()
                        )
                ).from(quizSolvedState)
                    .join(quizSolvedState.user, user)
                .where(
                    quizSolvedState.solvedState.eq(SolvedState.SOLVED)
                )
                .groupBy(quizSolvedState.user)
                .orderBy(quizSolvedState.id.count().desc(), quizSolvedState.user.id.asc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                .fetch();
    }

}
