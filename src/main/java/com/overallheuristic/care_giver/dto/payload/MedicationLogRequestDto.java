package com.overallheuristic.care_giver.dto.payload;

import com.overallheuristic.care_giver.utils.enums.MedicationAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLogRequestDto {
    private MedicationAction action;
    private LocalDateTime scheduledFor;
    private Long patientId;
}
