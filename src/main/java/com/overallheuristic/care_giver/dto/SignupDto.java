package com.overallheuristic.care_giver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank
    @Email
    @Size(min = 6)
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private Set<String> role;
}
