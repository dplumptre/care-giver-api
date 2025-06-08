package com.overallheuristic.care_giver.dto.payload;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResetRequestDto {

    @NotBlank
    public String password;
    @NotBlank
    public String confirmPassword;
    @NotBlank
    @Size(min = 6, max = 6, message = "Code must be exactly 6 characters")
    public String code;
}
