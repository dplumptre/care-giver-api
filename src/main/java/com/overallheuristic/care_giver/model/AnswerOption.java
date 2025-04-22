package com.overallheuristic.care_giver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOption {

        @Id
        @GeneratedValue(strategy =  GenerationType.IDENTITY)
        private Long id;
        private String answerOption;
        private Boolean isCorrect = false;


        @ManyToOne
        @JoinColumn(name = "question_id")
        @JsonIgnore
        private Question question;


        @CreationTimestamp
        @Column(name = "created_at",updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private LocalDateTime updatedAt;


}
