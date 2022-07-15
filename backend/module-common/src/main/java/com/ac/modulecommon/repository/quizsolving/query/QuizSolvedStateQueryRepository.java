package com.ac.modulecommon.repository.quizsolving.query;

import com.ac.modulecommon.entity.quizsolving.SolvedState;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ac.modulecommon.entity.quiz.QQuiz.quiz;
import static com.ac.modulecommon.entity.quizsolving.QQuizSolvedState.quizSolvedState;
import static com.ac.modulecommon.entity.quiztype.QQuizTypeMapping.quizTypeMapping;

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
}
