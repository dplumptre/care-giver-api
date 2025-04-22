package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.ExerciseSession;
import com.overallheuristic.care_giver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExerciseSessionRepository extends JpaRepository<ExerciseSession, Long> {
    Long countByUserAndPatientIsNotNull(User user);

    Long countByUserAndPatientIsNull(User user);

    @Query("SELECT DISTINCT es.createdAt FROM ExerciseSession es WHERE es.user = :user AND es.patient IS NOT NULL")
    List<LocalDateTime> findDistinctExerciseDatesByUserAndPatientNotNull( User user);

    @Query("SELECT DISTINCT es.createdAt FROM ExerciseSession es WHERE es.user = :user AND es.patient IS NULL")
    List<LocalDateTime> findDistinctExerciseDatesByUserAndPatientIsNull(User user);


}