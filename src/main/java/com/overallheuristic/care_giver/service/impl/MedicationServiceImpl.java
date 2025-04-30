package com.overallheuristic.care_giver.service.impl;

import com.overallheuristic.care_giver.dto.DosageTimeDto;
import com.overallheuristic.care_giver.dto.payload.MedicationRequestDto;
import com.overallheuristic.care_giver.exceptions.APIException;
import com.overallheuristic.care_giver.model.DosageTime;
import com.overallheuristic.care_giver.model.Medication;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.repositories.DosageTimeRepository;
import com.overallheuristic.care_giver.repositories.MedicationRepository;
import com.overallheuristic.care_giver.repositories.PatientRepository;
import com.overallheuristic.care_giver.service.MedicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicationServiceImpl implements MedicationService {
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DosageTimeRepository dosageTimeRepository;

    public MedicationServiceImpl(PatientRepository patientRepository, MedicationRepository medicationRepository, DosageTimeRepository dosageTimeRepository) {
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.dosageTimeRepository = dosageTimeRepository;
    }

    @Override
    @Transactional
    public String createMedicationWithDosageTimes(MedicationRequestDto request) {
        Patient patient = patientRepository.findById(request.getPatientId()).orElseThrow( ()-> new APIException("Patient not found"));
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
}
