package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupStatusRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.HomeSetupStatus;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.repositories.HomeSetupStatusRepository;
import com.overallheuristic.care_giver.repositories.PatientRepository;
import com.overallheuristic.care_giver.service.HomeSetupStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class HomeSetupStatusServiceImpl implements HomeSetupStatusService {
    private final PatientRepository patientRepository;
    private final HomeSetupStatusRepository homeSetupStatusRepository;
    private final ModelMapper modelMapper;

    public HomeSetupStatusServiceImpl(PatientRepository patientRepository, HomeSetupStatusRepository homeSetupStatusRepository, ModelMapper modelMapper) {
        this.patientRepository = patientRepository;
        this.homeSetupStatusRepository = homeSetupStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HomeSetupStatusDto getTaskStatus(Long patientId, HomeSetupStatusRequestDto homeSetupStatusRequestDto) {
        Patient patient = patientRepository.findById(patientId).orElseThrow( ()-> new RuntimeException("Patient not found"));
        HomeSetupStatus homeSetupStatus = homeSetupStatusRepository.findByPatient(patient);
        if (homeSetupStatus == null) {
            throw new APIException("Home setup status not found for this patient.");
        }
        return modelMapper.map(homeSetupStatus, HomeSetupStatusDto.class);
    }
}
