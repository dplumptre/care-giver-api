package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.HomeSetupStatusDto;
import com.overallheuristic.care_giver.dto.payload.HomeSetupUpdateRequest;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.*;
import com.overallheuristic.care_giver.repositories.*;
import com.overallheuristic.care_giver.service.HomeSetupStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomeSetupStatusServiceImpl implements HomeSetupStatusService {
    private final PatientRepository patientRepository;
    private final HomeSetupStatusRepository homeSetupStatusRepository;
    private  ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private HomeSetupResultRepository homeSetupResultRepository;

    public HomeSetupStatusServiceImpl(PatientRepository patientRepository, HomeSetupResultRepository homeSetupResultRepository, HomeSetupStatusRepository homeSetupStatusRepository, ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.homeSetupStatusRepository = homeSetupStatusRepository;
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.homeSetupResultRepository  = homeSetupResultRepository;
    }

    @Override
    public List<HomeSetupStatusDto> getTaskStatus(Long patientId, User user) {
        Patient patient = patientRepository.findById(patientId).orElseThrow( ()-> new RuntimeException("Patient not found"));
        List<HomeSetupStatus> homeSetupStatusList = homeSetupStatusRepository.findAllByPatient(patient);
        if (homeSetupStatusList.isEmpty()) {
            throw new APIException("Home setup status not found for this patient.");
        }

        return homeSetupStatusList.stream()
                .map(status -> {
                    HomeSetupStatusDto dto = new HomeSetupStatusDto();
                    dto.setId(status.getTask().getId());
                    dto.setTaskTitle(status.getTask().getTitle());
                    dto.setTaskDescription(status.getTask().getDescription());
                    dto.setIsCompleted(status.getIsCompleted());
                    dto.setUser(user);
                    return dto;
                }).toList();
    }

    @Override
    public void updateTaskStatus(Long patientId, HomeSetupUpdateRequest request,User user) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new APIException("Patient not found"));

        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new APIException("Task not found"));

        HomeSetupStatus status = homeSetupStatusRepository.findByPatientAndTask(patient, task)
                .orElseThrow(() -> new APIException("Home setup status not found for patient and task"));

        status.setIsCompleted(request.getIsCompleted());
        homeSetupStatusRepository.save(status);

        if(!request.getIsCompleted()) {
            HomeSetupResult selectedHomeSetup = homeSetupResultRepository.findByCarerAndPatient(user, patient);
            if(selectedHomeSetup != null) {
                homeSetupResultRepository.delete(selectedHomeSetup);
            }
        }

        List<HomeSetupStatus> allStatuses = homeSetupStatusRepository.findAllByCarerAndPatient(user, patient);

        boolean allComplete = allStatuses.stream().allMatch(HomeSetupStatus::getIsCompleted);

        if (!allComplete) {
            return;
        }

        boolean resultExists = homeSetupResultRepository.existsByCarerAndPatient(user, patient);
        if (resultExists) {
            return;
        }

        HomeSetupResult result = new HomeSetupResult();
        result.setCarer(user);
        result.setPatient(patient);
        result.setIsCompleted(true);
        result.setCompletedAt(LocalDateTime.now());
        homeSetupResultRepository.save(result);

    }

    @Override
    public boolean isSetupComplete(Long patientId, Long userId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<HomeSetupStatus> statuses = homeSetupStatusRepository.findAllByPatientAndCarer(patient, user);

        if (statuses.isEmpty()) return false;

        return statuses.stream().allMatch(HomeSetupStatus::getIsCompleted);
    }

    @Override
    public Integer getHomeStatusStars(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<HomeSetupResult> response = homeSetupResultRepository.findAllByCarerAndIsCompletedIsTrue(user);
        return response.size();
    }
}
