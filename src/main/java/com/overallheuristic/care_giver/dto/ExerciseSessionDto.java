package com.overallheuristic.care_giver.dto;

import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseSessionDto {

    private boolean isCareGiver ;
    private Video video;
    private Patient patient;
    private User user;
}
