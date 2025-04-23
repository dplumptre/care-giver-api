package com.overallheuristic.care_giver.dto;

import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.Task;
import com.overallheuristic.care_giver.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeSetupStatusDto {
    private Long id;
    private Boolean isCompleted = Boolean.FALSE;
    private String taskTitle;
    private String taskDescription;
}
