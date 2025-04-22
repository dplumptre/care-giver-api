package com.overallheuristic.care_giver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overallheuristic.care_giver.utils.enums.AffectedSide;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private AffectedSide affectedSide;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    List<ExerciseSession> exerciseSessions = new ArrayList<>();


}
