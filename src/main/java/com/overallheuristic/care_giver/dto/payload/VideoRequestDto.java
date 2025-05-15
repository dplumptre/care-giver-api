package com.overallheuristic.care_giver.dto.payload;

import com.overallheuristic.care_giver.utils.enums.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequestDto {
        @NotBlank(message = "Title is required")
        @Size(min=5)
        private String title;
        @NotBlank(message = "Description is required")
        private String description;
        @NotBlank(message = "Minimum of 5 characters is required")
        @Size(min=5)
        private String link;
        private ActivityType videoType;
    }


