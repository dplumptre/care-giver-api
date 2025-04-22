package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.UserProgressLearningHubResultDto;
import com.overallheuristic.care_giver.dto.UserProgressLearningHubDto;
import com.overallheuristic.care_giver.dto.payload.UserProgressRequestDto;
import com.overallheuristic.care_giver.model.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserProgressLearningHubService {
    UserProgressLearningHubDto create(@Valid UserProgressRequestDto userProgressRequestDto);

    List<UserProgressLearningHubDto> getUsersProgress(User user, boolean isSuccessful);

    UserProgressLearningHubResultDto getUsersProgressResult(User user, boolean isSuccessful);

    Boolean getUserLearningHubQuizStatus(User user, Long videoId);
}
