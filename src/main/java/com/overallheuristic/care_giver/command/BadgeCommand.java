package com.overallheuristic.care_giver.command;

import com.overallheuristic.care_giver.model.Badge;
import com.overallheuristic.care_giver.repositories.BadgeRepository;
import com.overallheuristic.care_giver.utils.enums.VideoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BadgeCommand implements CommandLineRunner {

    @Autowired
    private BadgeRepository badgeRepository;

    @Override
    public void run(String... args) throws Exception {


        Badge badge1 = Badge.builder()
                .name("Carer 1-Day Streak")
                .videoType(VideoType.CARER_EXERCISE)
                .streakDays(1)
                .icon("carer_1d.png")
                .build();

        Badge badge2 = Badge.builder()
                .name("Carer 7-Day Streak")
                .videoType(VideoType.CARER_EXERCISE)
                .streakDays(7)
                .icon("carer_7d.png")
                .build();

        Badge badge3 = Badge.builder()
                .name("Patient 1-Day Streak")
                .videoType(VideoType.PATIENT_EXERCISE_SUPPORT)
                .streakDays(1)
                .patientRequired(1)
                .icon("patient_1d.png")
                .build();

        Badge badge4 = Badge.builder()
                .name("Patient 7-Day Streak")
                .videoType(VideoType.PATIENT_EXERCISE_SUPPORT)
                .streakDays(7)
                .patientRequired(1)
                .icon("patient_7d.png")
                .build();

        Badge badge5 = Badge.builder()
                .name("Carer Medication Streak")
                .videoType(VideoType.MEDICATION)
                .streakDays(7)
                .icon("meds_7d.png")
                .build();

        List<Badge> demoBadges = List.of(badge1, badge2, badge3, badge4, badge5);

        for (Badge badge : demoBadges) {
            boolean exists = badgeRepository
                    .findByNameAndVideoTypeAndStreakDays(
                            badge.getName(),
                            badge.getVideoType(),
                            badge.getStreakDays()
                    ).isPresent();

            if (!exists) {
                badgeRepository.save(badge);
            }
        }
    }
}
