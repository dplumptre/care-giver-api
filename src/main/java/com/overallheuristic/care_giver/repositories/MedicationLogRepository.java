package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.MedicationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationLogRepository extends JpaRepository<MedicationLog, Long> {

}
