package com.ac.modulecommon.repository.quizlog;

import com.ac.modulecommon.entity.quizlog.QuizLog;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.ac.modulecommon.entity.quizlog.QQuizLog.quizLog;

@RequiredArgsConstructor
public class QuizLogCustomRepositoryImpl implements QuizLogCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<QuizLog> findFirstByUserId(Long userId) {
        QuizLog firstQuizLog = jpaQueryFactory
                .selectFrom(quizLog)
                .where(quizLog.user.id.eq(userId))
                .orderBy(quizLog.createdAt.asc())
                .fetchFirst();

        return Optional.ofNullable(firstQuizLog);
    }
}
