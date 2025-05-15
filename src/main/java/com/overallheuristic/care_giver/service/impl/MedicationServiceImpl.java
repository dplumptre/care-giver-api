package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.DosageTimeDto;
import com.overallheuristic.care_giver.dto.MedicationDto;
import com.overallheuristic.care_giver.dto.MedicationResultDto;
import com.overallheuristic.care_giver.dto.payload.MedicationLogRequestDto;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.*;
import com.overallheuristic.care_giver.repositories.*;
import com.overallheuristic.care_giver.service.MedicationService;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import com.overallheuristic.care_giver.utils.enums.MedicationAction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DosageTimeRepository dosageTimeRepository;
    private final ModelMapper modelMapper;
    private final MedicationLogRepository medicationLogRepository;
    private final BadgeRepository badgeRepository;

    public MedicationServiceImpl(PatientRepository patientRepository, MedicationRepository medicationRepository, DosageTimeRepository dosageTimeRepository, ModelMapper modelMapper, MedicationLogRepository medicationLogRepository, BadgeRepository badgeRepository) {
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.dosageTimeRepository = dosageTimeRepository;
        this.modelMapper = modelMapper;
        this.medicationLogRepository = medicationLogRepository;
        this.badgeRepository = badgeRepository;
    }

    @Override
    @Transactional
    public String createMedicationWithDosageTimes(MedicationRequestDto request) {
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow( ()-> new APIException("Patient not found"));

        Boolean status = medicationRepository.existsMedicationByPatientAndDrugName(patient,request.getDrugName());
        if(status){
            throw new APIException("Medication already exists");
        }

        Medication medication = new Medication();
        medication.setPatient(patient);
        medication.setDosage(request.getDosage());
        medication.setDrugName(request.getDrugName());
        Medication savedMedication = medicationRepository.save(medication);
        if(request.getDosageTimes() != null && !request.getDosageTimes().isEmpty()) {
            List<DosageTime> dosageTimeList = request.getDosageTimes().stream().map(dosageTime ->{
                 DosageTime dt = new DosageTime();
                 dt.setMedication(savedMedication);
                 dt.setTime(dosageTime.getTime());
                 return dt;
            }).toList();
            dosageTimeRepository.saveAll(dosageTimeList);
        }
        return "medication inserted successfully";
    }


    @Override
    public String deleteMedicationWithDosageTimes(Long medicationId) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow( ()-> new APIException("Medication not found"));
        medicationRepository.delete(medication);
        return "medication deleted successfully";
    }

    @Override
    public String updateMedicationWithDosageTimes(Long medicationId, MedicationRequestDto request) {
        Medication medication = medicationRepository.findById(medicationId).orElseThrow( ()-> new APIException("Medication not found"));
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow( ()-> new APIException("Patient not found"));
        medication.setDosage(request.getDosage());
        medication.setDrugName(request.getDrugName());
        medication.setPatient(patient);
        Medication savedMedication = medicationRepository.save(medication);

        List<DosageTime> existingDosages = dosageTimeRepository.findByMedicationId(medicationId);

        List<Long> incomingIds = request.getDosageTimes().stream()
                .filter(dto -> dto.getId() != null)
                .map(DosageTimeDto::getId)
                .toList();

        // Delete removed dosage times
        List<DosageTime> toDelete = existingDosages.stream()
                .filter(dt -> !incomingIds.contains(dt.getId()))
                .toList();
        dosageTimeRepository.deleteAll(toDelete);

        for (DosageTimeDto dto : request.getDosageTimes()) {
            if (dto.getId() != null) {
                DosageTime dt = dosageTimeRepository.findById(dto.getId())
                        .orElseThrow(() -> new APIException("DosageTime not found"));
                dt.setTime(dto.getTime());
                dosageTimeRepository.save(dt);
            } else {
                DosageTime newDt = new DosageTime();
                newDt.setTime(dto.getTime());
                newDt.setMedication(savedMedication);
                dosageTimeRepository.save(newDt);
            }
        }
        return "medication updated successfully";
    }

    @Override
    public List<MedicationDto> getMedications(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow( ()-> new APIException("Patient not found"));
        return medicationRepository.findAllByPatient(patient)
                .stream()
                .map(medication -> {
                    MedicationDto dto = new MedicationDto();
                    dto.setId(medication.getId());
                    dto.setDrugName(medication.getDrugName());
                    dto.setDosage(medication.getDosage());
                    dto.setPatient(patient);

                    if(medication.getDosageTime() != null && !medication.getDosageTime().isEmpty()) {
                        List<DosageTimeDto> dosageTimeList = medication.getDosageTime().stream().map(dosageTime -> {
                            DosageTimeDto dosageTimeDto = new DosageTimeDto();
                            dosageTimeDto.setId(dosageTime.getId());
                            dosageTimeDto.setTime(dosageTime.getTime());
                            return dosageTimeDto;
                        }).toList();

                   dto.setDosageTimes(dosageTimeList);
                   }
                   return dto;

                })
                .toList();

    }

    @Override
    public MedicationDto getMedication(Long medicationId) {
       Medication medication =  medicationRepository.findById(medicationId).orElseThrow( ()-> new APIException("Medication not found"));

        List<DosageTimeDto> dosageTimeList = medication.getDosageTime().stream().map(dosageTime ->{
            DosageTimeDto dosageTimeDto = new DosageTimeDto();
            dosageTimeDto.setId(dosageTime.getId());
            dosageTimeDto.setTime(dosageTime.getTime());
            return dosageTimeDto;
        }).toList();

        MedicationDto medicationDto = new MedicationDto();
        medicationDto.setId(medication.getId());
        medicationDto.setDrugName(medication.getDrugName());
        medicationDto.setDosage(medication.getDosage());
        medicationDto.setPatient(medication.getPatient());
        medicationDto.setDosageTimes(dosageTimeList);
        return medicationDto;

    }

    @Override
    public String createMedicationLog(MedicationLogRequestDto request) {

       Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow( ()-> new APIException("Patient not found"));
        MedicationLog medicationLog = new MedicationLog();
        medicationLog.setScheduledFor(request.getScheduledFor());
        medicationLog.setPatient(patient);
        medicationLog.setAction(request.getAction());
        medicationLog.setLoggedAt(LocalDateTime.now());
        medicationLogRepository.save(medicationLog);
        return "medication log created successfully";

    }


    public MedicationResultDto calculateStreakForPatient(Long patientId) {
        List<MedicationLog> logs = medicationLogRepository.findByPatientIdOrderByScheduledForAsc(patientId);
        if (logs.isEmpty()) return new MedicationResultDto(0, 0, 0, Collections.emptyList());

        int points = 0;

        // Set to collect unique days with at least one TAKE
        Set<LocalDate> takeDays = new HashSet<>();

        for (MedicationLog log : logs) {
            if ("TAKE".equalsIgnoreCase(log.getAction().toString())) {

                points++; // Each TAKE = 1 point

                // Only 1 entry per day needed for streak
                takeDays.add(log.getScheduledFor().toLocalDate());
            }
        }

        if (takeDays.isEmpty()) {
            return new MedicationResultDto(0, 0, 0, Collections.emptyList());
        }

        // Sort the unique TAKE days
        List<LocalDate> sortedDays = takeDays.stream().sorted().toList();

        int currentStreak = 1;
        int longestStreak = 1;

        for (int i = 1; i < sortedDays.size(); i++) {
            LocalDate prev = sortedDays.get(i - 1);
            LocalDate curr = sortedDays.get(i);

            if (curr.equals(prev.plusDays(1))) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }

            longestStreak = Math.max(longestStreak, currentStreak);
        }

        List<Badge> earnedBadges = badgeRepository.findByActivityTypeAndStreakDaysLessThanEqual(
                ActivityType.MEDICATION, currentStreak
        );

        return new MedicationResultDto(currentStreak, longestStreak, points, earnedBadges);
    }


}
