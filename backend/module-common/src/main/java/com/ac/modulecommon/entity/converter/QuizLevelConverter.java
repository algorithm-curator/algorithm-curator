package com.ac.modulecommon.entity.converter;

import com.ac.modulecommon.entity.quiz.QuizLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuizLevelConverter implements AttributeConverter<QuizLevel, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QuizLevel quizLevel) {
        return quizLevel.getCode();
    }

    @Override
    public QuizLevel convertToEntityAttribute(Integer quizLevelCode) {
        return QuizLevel.from(quizLevelCode);
    }
}
