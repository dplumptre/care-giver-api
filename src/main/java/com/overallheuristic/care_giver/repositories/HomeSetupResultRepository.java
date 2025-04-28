package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.HomeSetupResult;
import com.overallheuristic.care_giver.model.HomeSetupStatus;
import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeSetupResultRepository extends JpaRepository<HomeSetupResult, Long> {
    boolean existsByCarerAndPatient(User user, Patient patient);

    List<HomeSetupResult> findAllByCarerAndIsCompletedIsTrue(User user);

    HomeSetupResult findByCarerAndPatient(User user, Patient patient);
}
