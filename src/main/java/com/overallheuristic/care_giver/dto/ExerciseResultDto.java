package com.overallheuristic.care_giver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResultDto {
    private Integer patientStars;
    private Integer carerStars;
    private Map<Integer, String> patientStreaks;
    private Map<Integer, String> carerStreaks;

}

