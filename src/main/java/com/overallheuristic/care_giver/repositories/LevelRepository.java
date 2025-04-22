package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LevelRepository  extends JpaRepository<Level, Long> {
    Collection<Object> findByLevelName(String levelName);

    Level findLevelByLevelNumber(Integer currentLevel);
}
