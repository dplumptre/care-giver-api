package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.Question;
import com.overallheuristic.care_giver.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByVideo(Video video);

    Optional<Question> findByQuestion(String questionText);
}
