package com.overallheuristic.care_giver.controller;


import com.overallheuristic.care_giver.dto.APIResponse;
import com.overallheuristic.care_giver.dto.QuestionDto;
import com.overallheuristic.care_giver.dto.payload.QuestionRequestDto;
import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<QuestionDto>> createQuestion(@Valid @RequestBody QuestionRequestDto questionDto) {
        QuestionDto question = questionService.create(questionDto);
        APIResponse<QuestionDto> apiResponse = new APIResponse<>(true, "question created", question);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<QuestionDto>>> getAllQuestions() {
        List<QuestionDto> questionDtos = questionService.getQuestions();
        return ResponseEntity.ok(new APIResponse<>(true, "List of questions", questionDtos));
    }


    @GetMapping("/video/{videoId}")
    public ResponseEntity<APIResponse<List<QuestionDto>>> getAllQuestionsByVideo(@PathVariable Long videoId) {
        List<QuestionDto> questionDtos = questionService.getQuestionsByVideo(videoId);
        return ResponseEntity.ok(new APIResponse<>(true, "List of questions by specific video", questionDtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<QuestionDto>> updateQuestion(@PathVariable("id") Long id, @Valid @RequestBody QuestionRequestDto questionDto) {
        QuestionDto updatedQuestion = questionService.updateQuestion(id, questionDto);
        return ResponseEntity.ok(new APIResponse<>(true,"question updated successfully",updatedQuestion));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<APIResponse<String>> deleteQuestion(@PathVariable("id") Long id) {
       String resp =  questionService.deleteQuestion(id);
       return ResponseEntity.ok(new APIResponse<>(true,"question deleted",resp));
    }

}
