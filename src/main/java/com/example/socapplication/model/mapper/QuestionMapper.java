package com.example.socapplication.model.mapper;

import com.example.socapplication.model.dto.questionDto.ResponseQuestion;
import com.example.socapplication.model.entity.Question;

import java.util.Objects;

public class QuestionMapper {

    public static ResponseQuestion map(Question question) {
        if (Objects.isNull(question))
            return null;
        return new ResponseQuestion(
                question.getId(),
                question.getNickname(),
                question.getText(),
                question.getSentAt(),
                question.getStatus()
        );
    }
}
