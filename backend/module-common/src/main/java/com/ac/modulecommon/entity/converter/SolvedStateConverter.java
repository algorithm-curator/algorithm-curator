package com.ac.modulecommon.entity.converter;

import com.ac.modulecommon.entity.quizsolving.SolvedState;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SolvedStateConverter implements AttributeConverter<SolvedState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SolvedState solvedState) {
        return solvedState.getCode();
    }

    @Override
    public SolvedState convertToEntityAttribute(Integer solvedStateCode) {
        return SolvedState.from(solvedStateCode);
    }
}
