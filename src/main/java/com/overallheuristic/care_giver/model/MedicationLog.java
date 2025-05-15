package com.overallheuristic.care_giver.model;

import com.overallheuristic.care_giver.utils.enums.ActivityType;
import com.overallheuristic.care_giver.utils.enums.MedicationAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class MedicationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime loggedAt;
    @Enumerated(EnumType.STRING)
    private MedicationAction action;
    private LocalDateTime scheduledFor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}
