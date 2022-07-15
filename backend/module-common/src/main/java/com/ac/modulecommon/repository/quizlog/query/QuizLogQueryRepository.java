package com.ac.modulecommon.repository.quizlog.query;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ac.modulecommon.entity.quizlog.QQuizLog.quizLog;

@RequiredArgsConstructor
@Repository
public class QuizLogQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<QuizLogTraceQueryDto> getQuizLogTraces(Long userId, LocalDateTime time) {
        return jpaQueryFactory
                .select(
                    Projections.constructor(QuizLogTraceQueryDto.class,
                        localDateFormat(),
                        quizLog.solvedState.max()
                    )
                )
                .from(quizLog)
                .where(
                        quizLog.user.id.eq(userId),
                        quizLog.createdAt.between(time.minusMonths(1), time)
                )
                .groupBy(localDateFormat())
                .orderBy(localDateFormat().desc()).fetch();
    }

    private StringTemplate localDateFormat() {
        return Expressions
                .stringTemplate("DATE_FORMAT({0}, {1})", quizLog.createdAt, ConstantImpl.create("%Y-%m-%d"));
    }
}
