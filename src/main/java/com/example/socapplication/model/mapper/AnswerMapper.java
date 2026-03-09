package com.example.socapplication.model.mapper;

import com.example.socapplication.model.dto.answerDto.ResponseAnswer;
import com.example.socapplication.model.entity.Answer;

import java.util.Objects;

public class AnswerMapper {

    public static ResponseAnswer map(Answer answer) {
        if (Objects.isNull(answer))
            return null;
        return new ResponseAnswer(
                answer.getId(),
                answer.getQuestion(),
                answer.getAppUser(),
                answer.getText(),
                answer.getSentAt()
        );
    }
}
