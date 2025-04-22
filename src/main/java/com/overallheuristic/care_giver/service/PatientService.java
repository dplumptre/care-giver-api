package com.overallheuristic.care_giver.service;

import com.overallheuristic.care_giver.dto.PatientDto;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.utils.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PatientService {
    PatientDto createPatient(PatientDto patientDto, User user);

    List<PatientDto> getAll(User user);

    PatientDto getPatient(Long id);

    PatientDto update(@Valid PatientDto patientDto, Long id);

    String destroy(Long id);
}
