package com.overallheuristic.care_giver.repositories;

import com.overallheuristic.care_giver.model.AnswerOption;
import com.overallheuristic.care_giver.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
    List<AnswerOption> findByQuestionId(Long questionId);

    boolean existsByQuestionAndAnswerOption(Question question, String text);
}
