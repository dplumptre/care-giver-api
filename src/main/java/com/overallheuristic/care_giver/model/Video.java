package com.overallheuristic.care_giver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is required")
    @Size(min=5)
    private String title;
    @NotBlank(message = "Description is required")
    @Lob
    private String description;
    private String link;
    @Enumerated(EnumType.STRING)
    private VideoType videoType = VideoType.LEARNING_HUB;

    @OneToMany(mappedBy = "video")
    @JsonIgnore
    List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "video")
    @JsonIgnore
    List<ExerciseSession> exerciseSessions = new ArrayList<>();


    @OneToMany(mappedBy = "video")
    @JsonIgnore
    List <UserProgressLearningHub> userProgress = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
