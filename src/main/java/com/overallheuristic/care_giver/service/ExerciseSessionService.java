package com.overallheuristic.care_giver.service;


import com.overallheuristic.care_giver.dto.ExerciseResultDto;
import com.overallheuristic.care_giver.dto.ExerciseSessionDto;
import com.overallheuristic.care_giver.dto.payload.ExerciseSessionRequestDto;
import com.overallheuristic.care_giver.model.User;

public interface ExerciseSessionService {
    String completeSession(ExerciseSessionRequestDto exerciseSessionDto, Long patientId, User user);

    ExerciseResultDto getExerciseResult(User user);
}
