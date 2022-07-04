package com.ac.modulecommon.entity.converter;

import com.ac.modulecommon.entity.quiz.QuizPlatform;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuizPlatformConverter implements AttributeConverter<QuizPlatform, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QuizPlatform quizPlatform) {
        return quizPlatform.getCode();
    }

    @Override
    public QuizPlatform convertToEntityAttribute(Integer quizPlatformCode) {
        return QuizPlatform.from(quizPlatformCode);
    }
}
