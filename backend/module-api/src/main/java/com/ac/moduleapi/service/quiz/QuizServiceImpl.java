package com.ac.moduleapi.service.quiz;

import com.ac.modulecommon.entity.quiz.Quiz;
import com.ac.modulecommon.exception.ApiException;
import com.ac.modulecommon.repository.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ac.modulecommon.exception.EnumApiException.NOT_FOUND;
import static com.google.common.base.Preconditions.checkArgument;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Override
    public Quiz getQuiz(Long id) {
        checkArgument(id != null, "id 값은 필수입니다.");

        return quizRepository.findById(id)
                .orElseThrow(() -> new ApiException(NOT_FOUND, Quiz.class, String.format("id = %s", id)));
    }

    @Override
    public Long count() {
        return quizRepository.count();
    }
}
