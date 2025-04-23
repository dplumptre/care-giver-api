package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusRequestDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupUpdateRequest;

import java.util.List;

public interface HomeSetupStatusService {
    List<HomeSetupStatusDto> getTaskStatus(Long patientId);


    void updateTaskStatus(Long patientId, HomeSetupUpdateRequest request);
}
