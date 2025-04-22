package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.QuestionDto;
import com.overallheuristic.care_giver.dto.payload.QuestionRequestDto;
import jakarta.validation.Valid;

import java.util.List;


public interface QuestionService {
    QuestionDto create(@Valid QuestionRequestDto questionDto);
    List<QuestionDto> getQuestions();

    QuestionDto updateQuestion(Long id, @Valid QuestionRequestDto questionDto);

    String deleteQuestion(Long id);

    List<QuestionDto> getQuestionsByVideo(Long videoId);
}
