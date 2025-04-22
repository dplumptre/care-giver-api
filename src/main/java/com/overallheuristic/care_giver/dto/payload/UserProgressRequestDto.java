package com.overallheuristic.care_giver.dto.payload;

import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.Video;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressRequestDto {

    @NotNull
    @Schema(defaultValue = "false")
    private Boolean isSuccessful = false;
    @NotNull
    @Positive
    private Double score;
    @NotNull
    @Positive
    private Long videoId;
}


