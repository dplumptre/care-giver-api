package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.HomeSetupStatus;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeSetupStatusRepository extends JpaRepository<HomeSetupStatus, Long> {
    HomeSetupStatus findByPatient(Patient patient);

    boolean existsByPatientAndTask(Patient patient, Task task);
}
