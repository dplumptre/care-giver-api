package com.overallheuristic.care_giver.dto.payload;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicationRequestDto {
    private String drugName;
    private Integer dosage;
    private Long patientId;
    private List<LocalDateTime> dosageTimes;
}
