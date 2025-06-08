package com.overallheuristic.care_giver.dto.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailRequestDto {
    @NotBlank
    @Email
    private String email;
}
