package com.overallheuristic.care_giver.service;


import com.overallheuristic.care_giver.dto.AnswerOptionDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;


public interface AnswerOptionService {
    AnswerOptionDto addAnswer(Long questionId, AnswerOptionDto answerOptionDto);
}
