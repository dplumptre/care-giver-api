package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupUpdateRequest;
import com.overallheuristic.care_giver.model.User;

import java.util.List;

public interface HomeSetupStatusService {
    List<HomeSetupStatusDto> getTaskStatus(Long patientId, User user);


    void updateTaskStatus(Long patientId, HomeSetupUpdateRequest request, User user);

    boolean isSetupComplete(Long patientId, Long userId);

    Integer getHomeStatusStars(Long userId);

}
