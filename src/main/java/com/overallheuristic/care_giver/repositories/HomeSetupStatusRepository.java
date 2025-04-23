package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.HomeSetupStatus;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeSetupStatusRepository extends JpaRepository<HomeSetupStatus, Long> {
    HomeSetupStatus findByPatient(Patient patient);

    boolean existsByPatientAndTask(Patient patient, Task task);

    List<HomeSetupStatus> findAllByPatient(Patient patient);

    Optional<HomeSetupStatus> findByPatientAndTask(Patient patient, Task task);
}
