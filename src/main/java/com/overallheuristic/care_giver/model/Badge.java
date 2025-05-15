package com.overallheuristic.care_giver.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.overallheuristic.care_giver.utils.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    private Integer streakDays;
    @Column(name = "patient_required", nullable = true)
    private Integer patientRequired;
    private String icon;


    @OneToMany(mappedBy = "badge")
    @JsonIgnore
    List<UserBadge> userBadges = new ArrayList<>();


    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
