package com.overallheuristic.care_giver.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.model.Video;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressLearningHubDto {

    private Boolean isSuccessful;
    @NotBlank
    private Double score;
    @NotBlank
    private Video video;
    @JsonIgnore
    @NotBlank
    private User user;
}
