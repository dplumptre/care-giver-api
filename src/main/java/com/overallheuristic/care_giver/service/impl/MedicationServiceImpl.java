package com.overallheuristic.care_giver.service.impl;

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
                 dt.setTime(dosageTime);
                 return dt;
            }).toList();

            dosageTimeRepository.saveAll(dosageTimeList);
        }

        return "medication inserted successfully";
    }
}
