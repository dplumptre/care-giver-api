package com.overallheuristic.care_giver.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer levelNumber;
    private String levelName;
    @Schema(defaultValue = "100")
    private Integer score = 100;

    @OneToMany(mappedBy = "level")
    List<LearningHubReward> learningHubRewards = new ArrayList<>();

}
