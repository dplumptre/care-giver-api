package com.overallheuristic.care_giver.model;

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
public class ExerciseSession {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private boolean isCareGiver = false;

    @ManyToOne()
    @JoinColumn(name = "video_id")
    private Video video;


    @ManyToOne(optional = true)
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
