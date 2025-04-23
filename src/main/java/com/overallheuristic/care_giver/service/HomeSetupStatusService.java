package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusRequestDto;

public interface HomeSetupStatusService {
    HomeSetupStatusDto getTaskStatus(Long patientId, HomeSetupStatusRequestDto homeSetupStatusRequestDto);
}
