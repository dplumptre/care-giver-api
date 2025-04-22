package com.overallheuristic.care_giver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressLearningHubResultDto {

    private Integer currentLevel;
    private Integer Levels;
    private String  reward;
}
