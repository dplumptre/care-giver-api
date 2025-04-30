package com.overallheuristic.care_giver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DosageTimeDto {
    private Long id;
    private LocalDateTime time;
}
