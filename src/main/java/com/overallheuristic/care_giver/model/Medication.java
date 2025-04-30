package com.overallheuristic.care_giver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "medication",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"patient_id", "drug_name"})
        }
)
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String drugName;
    private Integer dosage;
    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DosageTime> dosageTime= new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}