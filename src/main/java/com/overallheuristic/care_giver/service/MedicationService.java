package com.overallheuristic.care_giver.service;


import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;

public interface MedicationService {
    String createMedicationWithDosageTimes(MedicationRequestDto request);
}
