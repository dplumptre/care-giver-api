package com.overallheuristic.care_giver.dto;


import com.overallheuristic.care_giver.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto {
    private Long id;
    private String drugName;
    private Integer dosage;
    private Patient patient;
    private List<DosageTimeDto> dosageTimes;
}
