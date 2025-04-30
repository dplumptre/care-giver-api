package com.overallheuristic.care_giver.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeSetupUpdateRequest {
    private Long taskId;
    private Boolean isCompleted;
}
