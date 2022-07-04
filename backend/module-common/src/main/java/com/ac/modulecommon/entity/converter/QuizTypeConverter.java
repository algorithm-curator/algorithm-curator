package com.ac.modulecommon.entity.converter;

import com.ac.modulecommon.entity.quiztype.QuizType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuizTypeConverter implements AttributeConverter<QuizType, Integer>  {
    @Override
    public Integer convertToDatabaseColumn(QuizType quizType) {
        return quizType.getCode();
    }

    @Override
    public QuizType convertToEntityAttribute(Integer quizTypeCode) {
        return QuizType.from(quizTypeCode);
    }
}
