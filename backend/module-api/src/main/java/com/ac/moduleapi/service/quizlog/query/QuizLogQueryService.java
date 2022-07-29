package com.ac.moduleapi.service.quizlog.query;

import com.ac.modulecommon.entity.quizlog.QuizLog;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quizlog.QuizLogRepository;
import com.ac.modulecommon.repository.quizlog.query.QuizLogQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.ac.modulecommon.exception.EnumApiException.NOT_FOUND;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizLogQueryService {

    private final QuizLogQueryRepository quizLogQueryRepository;
    private final QuizLogRepository quizLogRepository;

    public List<QuizLogTraceDto> getQuizLogTraces(Long userId, LocalDate localDate) {
        checkArgument(userId != null, "userId 값은 필수입니다.");
        checkArgument(localDate != null, "localDate 값은 필수입니다.");

        LocalDate firstDate = localDate.withDayOfMonth(1);
        LocalDateTime firstDateTime = firstDate.atStartOfDay();

        LocalDate lastDate = localDate.withDayOfMonth(localDate.lengthOfMonth());
        LocalDateTime lastDateTime = lastDate.atTime(LocalTime.of(23,59,59));

        return quizLogQueryRepository.getQuizLogTraces(userId, firstDateTime, lastDateTime)
                .stream()
                .map(QuizLogTraceDto::from)
                .collect(toList());
    }

    public LocalDateTime getFirstUsageTime(Long userId) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        return quizLogRepository.findFirstByUserId(userId)
                .orElseThrow(
                        () -> new ApiException(NOT_FOUND,
                        QuizLog.class,
                        String.format("userId = %s", userId))
                )
                .getCreatedAt();
    }
}
