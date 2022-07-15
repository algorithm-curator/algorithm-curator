package com.ac.moduleapi.service.quizsolving.query;

import com.ac.modulecommon.entity.quiztype.QuizType;
import com.ac.modulecommon.repository.quizsolving.query.QuizSolvedStateQueryRepository;
import com.ac.modulecommon.repository.quizsolving.query.SolvedCntByQuizTypeQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizSolvedStateQueryService {

    private final QuizSolvedStateQueryRepository queryRepository;

    public List<SolvedCntByQuizTypeQueryDto> getSolvedCountByQuizTypes(Long userId) {
        checkArgument(userId != null, "userId 값은 필수입니다.");

        Map<QuizType, Long> solvingCountByQuizTypeMap = queryRepository.findAll(userId)
                .stream()
                .collect(toMap(SolvedCntByQuizTypeQueryDto::getQuizType, SolvedCntByQuizTypeQueryDto::getCount));

        return Arrays.stream(QuizType.values()).map(quizType -> {
            long count = solvingCountByQuizTypeMap.getOrDefault(quizType, 0L);
            return new SolvedCntByQuizTypeQueryDto(quizType, count);
        }).collect(toList());
    }
}
