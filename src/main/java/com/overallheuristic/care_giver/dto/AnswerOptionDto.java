package com.overallheuristic.care_giver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOptionDto {

    @NotBlank(message = "Answer option is required")
    private String answerOption;
    private Boolean isCorrect = false;

    public static class ExerciseResultDto {
    }
}
