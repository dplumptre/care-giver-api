package com.overallheuristic.care_giver.dto;
import com.overallheuristic.care_giver.model.AnswerOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String question;
    List<AnswerOption> answerOptionList = new ArrayList<>();
}
