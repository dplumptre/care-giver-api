package com.overallheuristic.care_giver.controller;

import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.AnswerOptionDto;
import com.overallheuristic.care_giver.model.AnswerOption;
import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.service.AnswerOptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/answer-options")

public class AnswerOptionController {

    @Autowired
    private AnswerOptionService answerOptionService;

    @PostMapping("question/{questionId}")
    public ResponseEntity<APIResponse<AnswerOptionDto>> addAnswerOption(@PathVariable("questionId") Long questionId, @Valid @RequestBody AnswerOptionDto answerOptionDto) {
        AnswerOptionDto savedAnswerOption  = answerOptionService.addAnswer(questionId, answerOptionDto);
        return new ResponseEntity<>(new APIResponse<>(true,"Answer option created sucessfully",savedAnswerOption), HttpStatus.CREATED);
    }



}
