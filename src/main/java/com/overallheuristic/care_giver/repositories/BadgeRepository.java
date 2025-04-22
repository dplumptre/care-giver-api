package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Object> findByNameAndVideoTypeAndStreakDays(String name, VideoType videoType, Integer streakDays);
}
