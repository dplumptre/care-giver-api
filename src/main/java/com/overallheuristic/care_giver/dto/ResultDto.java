package com.overallheuristic.care_giver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {
    private Integer patientStars;
    private Integer carerStars;
    private Integer homeSetupStars;
    private Integer medicalStars;
    private String medicalAdherenceBadge;
}
