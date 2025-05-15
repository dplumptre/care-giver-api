package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.model.MedicationLog;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Object> findByNameAndActivityTypeAndStreakDays(String name, ActivityType activityType, Integer streakDays);
    List<Badge> findByActivityTypeAndStreakDaysLessThanEqual(ActivityType activityType, int streakDays);

}
