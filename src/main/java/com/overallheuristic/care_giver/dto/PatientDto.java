package com.overallheuristic.care_giver.dto;


import com.overallheuristic.care_giver.utils.enums.AffectedSide;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private Long id;
    @NotBlank
    @Size(min = 3)
    private String name;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
    @NotNull
    private AffectedSide affectedSide;
}
