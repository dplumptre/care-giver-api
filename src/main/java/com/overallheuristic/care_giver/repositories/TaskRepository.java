package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Object> findByTitleAndDescription(String title, String description);
}
