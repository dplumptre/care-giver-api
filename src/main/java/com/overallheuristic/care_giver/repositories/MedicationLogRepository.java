package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.MedicationLog;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.utils.enums.MedicationAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {
    List<MedicationLog> findByPatientIdOrderByScheduledForAsc(Long patientId);
    @Query("SELECT COUNT(m) FROM MedicationLog m WHERE m.patient.user = :user AND m.action = :action")
    Object countByPatientUserAndAction(User user, MedicationAction medicationAction);

    List<MedicationLog> findByPatientUser(User user);

//    long countByPatientUserAndAction(User user, MedicationAction action);




}
