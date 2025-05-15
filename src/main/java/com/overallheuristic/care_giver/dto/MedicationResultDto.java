package com.overallheuristic.care_giver.dto;

import com.overallheuristic.care_giver.model.Badge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationResultDto {
    private int currentStreak;
    private int longestStreak;
    private int points; // based on number of meds taken
    private List<Badge> earnedBadges;
}
