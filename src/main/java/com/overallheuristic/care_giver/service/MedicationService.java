package com.overallheuristic.care_giver.service;


import com.overallheuristic.care_giver.dto.MedicationDto;
import com.overallheuristic.care_giver.dto.MedicationResultDto;
import com.overallheuristic.care_giver.dto.payload.MedicationLogRequestDto;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;

import java.util.List;

public interface MedicationService {
    String createMedicationWithDosageTimes(MedicationRequestDto request);

    String deleteMedicationWithDosageTimes(Long medicationId);

    String updateMedicationWithDosageTimes(Long medicationId, MedicationRequestDto request);

    List<MedicationDto> getMedications(Long patientId);

    MedicationDto getMedication(Long medicationId);

    String createMedicationLog(MedicationLogRequestDto request);

    MedicationResultDto calculateStreakForPatient(Long patientId);
}
