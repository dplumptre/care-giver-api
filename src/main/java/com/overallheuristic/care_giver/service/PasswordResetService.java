package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.payload.EmailRequestDto;
import com.overallheuristic.care_giver.dto.payload.ResetRequestDto;
import jakarta.validation.Valid;

public interface PasswordResetService {
    String passwordReset( EmailRequestDto email);

    String passwordResetByCode(@Valid ResetRequestDto resetDto, String email);
}
