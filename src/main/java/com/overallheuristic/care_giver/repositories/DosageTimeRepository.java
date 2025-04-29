package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.DosageTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DosageTimeRepository extends JpaRepository<DosageTime, Integer> {
}
