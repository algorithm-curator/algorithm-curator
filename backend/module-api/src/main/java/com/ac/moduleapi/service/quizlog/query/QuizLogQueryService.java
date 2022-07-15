package com.ac.moduleapi.service.quizlog.query;

import com.ac.modulecommon.repository.quizlog.query.QuizLogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizLogQueryService {

    private final QuizLogQueryRepository quizLogQueryRepository;

    public List<QuizLogTraceDto> getQuizLogTraces(Long userId, LocalDateTime localDateTime) {
        checkArgument(userId != null, "userId 값은 필수입니다.");
        checkArgument(localDateTime != null, "localDateTime 값은 필수입니다.");

        return quizLogQueryRepository.getQuizLogTraces(userId, localDateTime)
                .stream()
                .map(QuizLogTraceDto::from)
                .collect(toList());
    }
}
