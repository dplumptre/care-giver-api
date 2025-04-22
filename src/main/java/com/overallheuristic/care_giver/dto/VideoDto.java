package com.overallheuristic.care_giver.dto;

import com.overallheuristic.care_giver.utils.enums.VideoType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min=5)
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Minimum of 5 characters is required")
    @Size(min=5)
    private String link;
    @NotBlank(message = "Video Type is required")
    private VideoType videoType;
}
