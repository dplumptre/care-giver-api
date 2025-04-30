package com.overallheuristic.care_giver.dto.payload;

import com.overallheuristic.care_giver.dto.DosageTimeDto;
import com.overallheuristic.care_giver.model.DosageTime;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MedicationRequestDto {
    private String drugName;
    private Integer dosage;
    private Long patientId;
    private List<DosageTimeDto> dosageTimes;
}
