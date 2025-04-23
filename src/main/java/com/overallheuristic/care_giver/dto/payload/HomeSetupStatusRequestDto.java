package com.overallheuristic.care_giver.dto.payload;

import com.overallheuristic.care_giver.model.Patient;
import com.overallheuristic.care_giver.model.Task;
import com.overallheuristic.care_giver.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeSetupStatusRequestDto {
    private Boolean isCompleted = Boolean.FALSE;
}
