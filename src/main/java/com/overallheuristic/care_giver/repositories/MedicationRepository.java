package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Medication;
import com.overallheuristic.care_giver.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findAllByPatient(Patient patient);

    Boolean existsMedicationByPatientAndDrugName(Patient patient, String drugName);
}
